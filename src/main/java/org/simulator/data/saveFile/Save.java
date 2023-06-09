package org.simulator.data.saveFile;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simulator.board.Board;
import org.simulator.board.Direction;
import org.simulator.board.Tile;
import org.simulator.controls.SimulatorLogic;
import org.simulator.controls.UniversalAdapter;

import java.awt.event.ActionEvent;
import java.io.FileWriter;


public class Save extends UniversalAdapter {
    private SimulatorLogic logic;
    public Save(SimulatorLogic logic){
        this.logic = logic;
    }
    public static JSONObject saveTile(Tile tile){
        JSONObject jsonTile = new JSONObject();
        if(!tile.getNeighbours().isEmpty()){
            JSONObject neighbour = new JSONObject();
            for(Direction direction: Direction.values()){
                if(tile.getNeighbours().containsKey(direction)) {
                    neighbour.put(direction.toString(),tile.getNeighbours().get(direction).isConnected());
                    jsonTile.put("neighbour",neighbour);
                }
            }

        }
        jsonTile.put("State",tile.getStateType());
        return jsonTile;

    }

    public static void saveBoardToJson(Board board, String path)/*throws JSException, IOException */{
        JSONObject jsonBoard = new JSONObject();
        JSONArray column = new JSONArray();
        for(int i = 0; i < board.getXDimension(); i++){
            JSONArray rowArray = new JSONArray();
            for(int j = 0; j < board.getYDimension(); j++){
                rowArray.put(saveTile(board.getBoard()[i][j]));
                System.out.println(board.getBoard()[i][j].getStateType());
            }
            column.put(rowArray);
        }
        jsonBoard.put("Board",column);
        try (FileWriter fileWriter = new FileWriter(path)) {
            fileWriter.write(jsonBoard.toString());
        }catch(Exception e){
            System.out.println("trouble");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        saveBoardToJson(logic.getBoard(),logic.getPath());
        System.out.println(logic.getPath());
    }
}
