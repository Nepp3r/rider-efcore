package com.jetbrains.rider.plugins.efcore.cli.api

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.jetbrains.rider.plugins.efcore.EfCoreUiBundle
import com.jetbrains.rider.plugins.efcore.cli.api.models.DotnetEfVersion
import com.jetbrains.rider.plugins.efcore.cli.execution.CommonOptions
import com.jetbrains.rider.plugins.efcore.cli.execution.DotnetCommand
import com.jetbrains.rider.plugins.efcore.cli.execution.EfCoreCommandBuilder
import com.jetbrains.rider.plugins.efcore.cli.execution.KnownEfCommands

@Service(Service.Level.PROJECT)
class DbContextCommandFactory(private val intellijProject: Project) {
    companion object {
        fun getInstance(project: Project) = project.service<DbContextCommandFactory>()
    }

    fun scaffold(efCoreVersion: DotnetEfVersion, options: CommonOptions, connection: String, provider: String,
                 outputFolder: String, useAttributes: Boolean, useDatabaseNames: Boolean, generateOnConfiguring: Boolean,
                 usePluralizer: Boolean, dbContextName: String, dbContextFolder: String, scaffoldAllTables: Boolean,
                 tablesList: List<String>, scaffoldAllSchemas: Boolean, schemasList: List<String>): DotnetCommand =
        EfCoreCommandBuilder(intellijProject, KnownEfCommands.DbContext.scaffold, options, EfCoreUiBundle.message("scaffold.dbcontext.presentable.name")).apply {
            add(connection)
            add(provider)

            addIf("--data-annotations", useAttributes)
            addNamed("--context", dbContextName)
            addNamed("--context-dir", dbContextFolder)
            add("--force")
            addNamed("--output-dir", outputFolder)

            if (!scaffoldAllSchemas) {
                schemasList.forEach {
                    addNamed("--schema", it)
                }
            }

            if (!scaffoldAllTables) {
                tablesList
                    .filter { it.isNotEmpty() }
                    .forEach { addNamed("--table", it) }
            }

            addIf("--use-database-names", useDatabaseNames)

            if (efCoreVersion.major >= 5) {
                addIf("--no-onconfiguring", !generateOnConfiguring)
                addIf("--no-pluralize", !usePluralizer)
            }
        }.build()
}