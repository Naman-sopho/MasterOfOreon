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
import org.terasology.rendering.nui.UIWidget;
import org.terasology.rendering.nui.widgets.ItemSelectEventListener;
import org.terasology.rendering.nui.widgets.UIButton;
import org.terasology.rendering.nui.widgets.UIList;
import org.terasology.taskSystem.AssignedTaskType;
import org.terasology.taskSystem.BuildingType;
import org.terasology.taskSystem.events.SetTaskTypeEvent;

import java.util.ArrayList;
import java.util.List;

public class TaskSelectionScreenLayer extends CoreScreenLayer {
    @In
    private LocalPlayer localPlayer;

    @In
    private NUIManager nuiManager;

    private UIButton cancelButton;

    private UIList<String> tasksList;
    private UIList<String> buildingsList;

    @Override
    public void initialise() {
        cancelButton = find(MooConstants.CANCEL_BUTTON_ID, UIButton.class);

        populateTasksList();
        populateBuildingsList();

        cancelButton.subscribe(button -> {
            sendSetTaskTypeEvent();
        });
    }

    private void sendSetTaskTypeEvent () {
        localPlayer.getCharacterEntity().send(new SetTaskTypeEvent());
    }

    private void sendSetTaskTypeEvent(String assignedTaskType) {
        localPlayer.getCharacterEntity().send(new SetTaskTypeEvent(assignedTaskType));
    }

    private void sendSetTaskTypeEvent (String assignedTaskType, BuildingType buildingType) {
        localPlayer.getCharacterEntity().send(new SetTaskTypeEvent(assignedTaskType, buildingType));
    }

    private void populateBuildingsList() {
        buildingsList = find(MooConstants.BUILDINGS_LIST_ID, UIList.class);
        buildingsList.subscribeSelection(new ItemSelectEventListener<String>() {
            @Override
            public void onItemSelected(UIWidget widget, String item) {
                sendSetTaskTypeEvent(AssignedTaskType.BUILD, BuildingType.valueOf(item));
            }
        });

        List<String> buttonList = new ArrayList<>();
        buttonList.add("Diner");
        buttonList.add("Storage");
        buttonList.add("Laboratory");
        buttonList.add("Hospital");
        buttonList.add("Jail");
        buildingsList.setList(buttonList);
    }

    private void populateTasksList() {
        tasksList = find(MooConstants.TASKS_LIST_ID, UIList.class);

        tasksList.subscribeSelection(new ItemSelectEventListener<String>() {
            @Override
            public void onItemSelected(UIWidget widget, String item) {
                String task = AssignedTaskType.NONE;
                switch(item) {
                    case "Plant" :
                        task = AssignedTaskType.PLANT;
                        break;

                    case "Guard":
                        task = AssignedTaskType.GUARD;
                        break;
                }
                sendSetTaskTypeEvent(task);
            }
        });

        List<String> buttonList = new ArrayList<>();
        buttonList.add("Plant");
        buttonList.add("Guard");
        tasksList.setList(buttonList);
    }
}
