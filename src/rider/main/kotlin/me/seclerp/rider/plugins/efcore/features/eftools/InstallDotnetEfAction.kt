package me.seclerp.rider.plugins.efcore.features.eftools

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import me.seclerp.rider.plugins.efcore.cli.api.ManagementCommandFactory
import me.seclerp.rider.plugins.efcore.cli.execution.executeCommandUnderProgress

class InstallDotnetEfAction : AnAction("Fix") {
    override fun actionPerformed(actionEvent: AnActionEvent) {
        executeCommandUnderProgress(
            actionEvent.project!!,
            "Installing EF Core global tools...",
            "EF Core global tools have been successfully installed",
            false
        ) {
            val managementCommandFactory = actionEvent.project!!.service<ManagementCommandFactory>()
            managementCommandFactory.installEfCoreTools().execute()
        }
    }
}