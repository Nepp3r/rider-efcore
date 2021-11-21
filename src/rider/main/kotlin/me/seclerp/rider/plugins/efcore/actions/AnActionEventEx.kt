package me.seclerp.rider.plugins.efcore.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.vfs.VirtualFile
import com.jetbrains.rider.projectView.workspace.ProjectModelEntity
import com.jetbrains.rider.projectView.workspace.containingEntity
import com.jetbrains.rider.projectView.workspace.getId

fun AnActionEvent.isProjectFile(): Boolean {
    val actionFile = getData(PlatformDataKeys.VIRTUAL_FILE) ?: return false

    return actionFile.extension.equals("csproj")
}

fun AnActionEvent.getDotnetProjectName(): String = getDotnetProject().name

fun AnActionEvent.getDotnetProject(): ProjectModelEntity {
    val actionFile = getData(PlatformDataKeys.VIRTUAL_FILE)!!
    val projectModelEntity = actionFile.containingEntity(project!!)
    return projectModelEntity!!
}