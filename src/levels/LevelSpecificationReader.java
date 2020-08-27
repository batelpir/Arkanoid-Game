package levels;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import game.LevelInformation;
import general.Velocity;
import sprites.Block;
/**
 * @author batel pirov.
 * has one method which read the levels file.
 */
public class LevelSpecificationReader {
    /**
     * read the levels file and makes them to be a list of level information.
     * @param reader - a reader.
     * @return list of level information.
     * @throws IOException - a possible exception.
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) throws IOException {
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(reader);
        int counter = 0;
        // the information:
        String levelName = null;
        List<Velocity> ballVelocities = null;
        BackgroundMaker background = null;
        int paddleSpeed = 0;
        int paddleWidth = 0;
        String blockFileName = null;
        List<Block> blockList = new ArrayList<>();
        int blockStartX = 0;
        int blockStartY = 0;
        int rowHeight = 0;
        int numBlocks = 0;
        List<LevelInformation> levels = new ArrayList<>();
        String line;
        try {
            // reading all the file's lines.
            line = bufferedReader.readLine();
            while (line != null) {
                if (line.equals("START_LEVEL")) {
                    LevelFromFile oneLevel;
                    line = bufferedReader.readLine();
                    while (!line.equals("START_BLOCKS")) {
                        String[] parts = line.split(":");
                        String part1 = parts[0]; // key.
                        String part2 = parts[1]; // value.
                        if (part1.equals("level_name")) {
                            counter++;
                            levelName = part2;
                        }
                        if (part1.equals("ball_velocities")) {
                            counter++;
                            ballVelocities = new ArrayList<Velocity>();
                            String[] speedAndVelocities = part2.split(" ");
                            for (String s : speedAndVelocities) {
                                String[] oneVelo = s.split(",");
                                double angle = Double.parseDouble(oneVelo[0]);
                                double speed = Double.parseDouble(oneVelo[1]);
                                Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
                                ballVelocities.add(v);
                            }
                        }
                        if (part1.equals("background")) {
                            counter++;
                            background = new BackgroundMaker(part2); // this is a sprite
                        }
                        if (part1.equals("paddle_speed")) {
                            counter++;
                            paddleSpeed = Integer.parseInt(part2);
                        }
                        if (part1.equals("paddle_width")) {
                            counter++;
                            paddleWidth = Integer.parseInt(part2);
                        }
                        if (part1.equals("block_definitions")) {
                            counter++;
                            blockFileName = part2;
                        }
                        if (part1.equals("blocks_start_x")) {
                            counter++;
                            blockStartX = Integer.parseInt(part2);
                        }
                        if (part1.equals("blocks_start_y")) {
                            counter++;
                            blockStartY = Integer.parseInt(part2);
                        }
                        if (part1.equals("row_height")) {
                            counter++;
                            rowHeight = Integer.parseInt(part2);
                        }
                        if (part1.equals("num_blocks")) {
                            counter++;
                            numBlocks = Integer.parseInt(part2);
                        }
                        line = bufferedReader.readLine();
                    }
                    if (counter < 10) {
                        System.out.println("missing fields");
                        throw new IOException("missing field");
                    }
                    // the end of the loop, so we arrived to "START_BLOCK" line.
                    // create list of Blocks:
                    InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blockFileName);
                    Reader blocksReader =  new InputStreamReader(is);
                    BlocksFromSymbolsFactory bfsf = BlocksDefinitionReader.fromReader(blocksReader);
                    line = bufferedReader.readLine();
                    while (!line.equals("END_BLOCKS")) {
                        if (!(line.equals(""))) {
                            // read one character every time:
                            int tempStartX = blockStartX;
                            int tempStartY = blockStartY;
                            for (int i = 0; i < line.length(); i++) {
                                String currentString = String.valueOf(line.charAt(i));
                                if (bfsf.isSpaceSymbol(currentString)) {
                                    tempStartX += bfsf.getSpaceWidth(String.valueOf(line.charAt(i)));
                                }
                                if (bfsf.isBlockSymbol(currentString)) {
                                    Block block = bfsf.getBlock(currentString, tempStartX, tempStartY);
                                    blockList.add(block);
                                    tempStartX += block.getWidth();
                                }
                            }
                            blockStartY += rowHeight;
                        }
                        line = bufferedReader.readLine();
                    }
                    oneLevel = new LevelFromFile(ballVelocities, paddleSpeed, paddleWidth, levelName,
                            background, blockList, numBlocks);
                    levels.add(oneLevel);
                    counter = 0;
                    // clear the information from the variables between two levels:
                    levelName = null;
                    ballVelocities = null;
                    background = null;
                    paddleSpeed = 0;
                    paddleWidth = 0;
                    blockFileName = null;
                    blockList = new ArrayList<>();
                    blockStartX = 0;
                    blockStartY = 0;
                    rowHeight = 0;
                    numBlocks = 0;
                }
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("cannot read level spesification");
        }
        return levels;
    }
}
