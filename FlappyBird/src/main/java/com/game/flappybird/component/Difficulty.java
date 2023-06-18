package com.game.flappybird.component;

import com.game.flappybird.util.Constant;
import com.game.flappybird.util.GameUtil;
import javax.swing.JOptionPane;

public class Difficulty {
    private static String state;

    public static String getDifficulty() {
        setDifficulty();
        return "EASY".equals(state) ? "Easy" : "MEDIUM".equals(state) ? "Medium" : "ASIAN MODE";
    }
    
    public static void setDifficulty() {
        if(Constant.GAME_SPEED < 5) {
            state = "EASY";
        } else if (Constant.GAME_SPEED > 5 && Constant.GAME_SPEED < 10) {
            state = "MEDIUM";
        }
    }
    
    private String selectedDifficulty;

    public Difficulty() {
        selectDifficulty();
    }

    private void selectDifficulty() {
        String[] difficultyOptions = {"Easy", "Medium", "Hard"};
        selectedDifficulty = (String) JOptionPane.showInputDialog(
                null,
                "Select Difficulty:",
                "Difficulty Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                difficultyOptions,
                difficultyOptions[0]
        );

        if (selectedDifficulty == null) {
            // Handle case when no difficulty is selected
            // Default to a certain difficulty or show an error message
            selectedDifficulty = difficultyOptions[0]; // Default to the first difficulty option
        }
    }

    public String getSelectedDifficulty() {
        return selectedDifficulty;
    }
    
    private void retrieveUserValue(String message, int minValue, int maxValue) throws Exception {
        int userValue = 0;
        boolean validInput = false;

        while (!validInput) {
            String inputValue = JOptionPane.showInputDialog(null, message);

            try {
                userValue = Integer.parseInt(inputValue);
                if (userValue >= minValue && userValue <= maxValue) {
                    validInput = true;
                    int max = maxValue == 5 ? 1 : maxValue == 10 ? 2 : 3;
                    if(GameUtil.isInProbability(1, 2))
                        userValue += GameUtil.getRandomNumber(1, max);
                } else {
                    JOptionPane.showMessageDialog(null, "Input hanya boleh diantara range!.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Input hanya bisa angka!.");
            }
        }
        Constant.GAME_SPEED = userValue;
    }
    
    public static void reference(Difficulty difficulty) throws Exception {
        if(null != difficulty.getSelectedDifficulty()) switch (difficulty.getSelectedDifficulty()) {
            case "Easy" -> difficulty.retrieveUserValue("Speed Game Range: 1 - 5", 1, 5);
            case "Medium" -> difficulty.retrieveUserValue("Speed Game Range: 5 - 10", 5, 10);
            case "Hard" -> difficulty.retrieveUserValue("Speed Game Range: 10 - 20", 10, 20);
            default -> {
            }
        }
    }
}
