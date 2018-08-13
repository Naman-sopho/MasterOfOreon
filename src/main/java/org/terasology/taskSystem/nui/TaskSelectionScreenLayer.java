/*
 * Copyright 2018 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.taskSystem.nui;

import org.terasology.MooConstants;
import org.terasology.logic.players.LocalPlayer;
import org.terasology.registry.In;
import org.terasology.rendering.nui.CoreScreenLayer;
import org.terasology.rendering.nui.NUIManager;
import org.terasology.rendering.nui.widgets.UIButton;
import org.terasology.taskSystem.AssignedTaskType;
import org.terasology.taskSystem.BuildingType;
import org.terasology.taskSystem.events.SetTaskTypeEvent;

public class TaskSelectionScreenLayer extends CoreScreenLayer {
    @In
    private LocalPlayer localPlayer;

    @In
    private NUIManager nuiManager;

    private UIButton plantCommand;
    private UIButton guardCommand;

    private UIButton hospitalButton;
    private UIButton dinerButton;
    private UIButton gymButton;
    private UIButton classroomButton;
    private UIButton storageButton;
    private UIButton laboratoryButton;
    private UIButton jailButton;

    private UIButton cancelButton;

    @Override
    public void initialise() {
        plantCommand = find(MooConstants.PLANT_COMMAND_UI_ID, UIButton.class);
        guardCommand = find(MooConstants.GUARD_COMMAND_UI_ID, UIButton.class);

        hospitalButton = find(MooConstants.HOSPITAL_BUTTON_ID, UIButton.class);
        dinerButton = find(MooConstants.DINER_BUTTON_ID, UIButton.class);
        gymButton = find(MooConstants.GYM_BUTTON_ID, UIButton.class);
        classroomButton = find(MooConstants.CLASSROOM_BUTTON_ID, UIButton.class);
        storageButton = find(MooConstants.STORAGE_BUTTON_ID, UIButton.class);
        laboratoryButton = find(MooConstants.LABORATORY_BUTTON_ID, UIButton.class);
        jailButton = find(MooConstants.JAIL_BUTTON_ID, UIButton.class);


        cancelButton = find(MooConstants.CANCEL_BUTTON_ID, UIButton.class);

        plantCommand.subscribe(button -> {
            sendSetTaskTypeEvent(AssignedTaskType.PLANT);
        });

        guardCommand.subscribe(button -> {
            sendSetTaskTypeEvent(AssignedTaskType.GUARD);
        });

        hospitalButton.subscribe(button -> {
            sendSetTaskTypeEvent(AssignedTaskType.BUILD, BuildingType.Hospital);
        });

        dinerButton.subscribe(button -> {
            sendSetTaskTypeEvent(AssignedTaskType.BUILD, BuildingType.Diner);
        });

        gymButton.subscribe(button -> {
            sendSetTaskTypeEvent(AssignedTaskType.BUILD, BuildingType.Gym);
        });

        classroomButton.subscribe(button -> {
            sendSetTaskTypeEvent(AssignedTaskType.BUILD, BuildingType.Classroom);
        });

        storageButton.subscribe(button -> {
            sendSetTaskTypeEvent(AssignedTaskType.BUILD, BuildingType.Storage);
        });

        laboratoryButton.subscribe(button -> {
            sendSetTaskTypeEvent(AssignedTaskType.BUILD, BuildingType.Laboratory);
        });

        jailButton.subscribe(button -> {
            sendSetTaskTypeEvent(AssignedTaskType.Build, BuildingType.Jail);
        });

        cancelButton.subscribe(button -> {
            sendSetTaskTypeEvent();
        });
    }

    public void sendSetTaskTypeEvent () {
        localPlayer.getCharacterEntity().send(new SetTaskTypeEvent());
    }

    public void sendSetTaskTypeEvent(String assignedTaskType) {
        localPlayer.getCharacterEntity().send(new SetTaskTypeEvent(assignedTaskType));
    }

    public void sendSetTaskTypeEvent (String assignedTaskType, BuildingType buildingType) {
        localPlayer.getCharacterEntity().send(new SetTaskTypeEvent(assignedTaskType, buildingType));
    }
}
