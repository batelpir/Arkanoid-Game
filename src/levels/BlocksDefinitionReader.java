package levels;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
/**
 * @author batel.
 * has a method which read the block definitions.
 */
public class BlocksDefinitionReader {
    /**
     * read the block definitions.
     * @param reader - the reader.
     * @return an object of block from symbols factory.
     * @throws IOException a possible exception.
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) throws IOException {
        BufferedReader bufferedReader = null;
        bufferedReader = new BufferedReader(reader);
        Map<String, Integer> spacerWidths = new TreeMap<>();
        Map<String, BlockCreator> blockCreators = new TreeMap<>();
        Map<String, String> defaultMap = new TreeMap<>();
        Map<String, String> oneSymbolMap = null;
        int counter = 0;
        String symbol = null;
        int width = -1;
        int height = -1;
        int hitPoints = -1;
        Map<Integer, String> fillMap = null;
        Color stroke = null;
        String line;
        try {
            // reading all the file's lines.
            while ((line = bufferedReader.readLine()) != null) {
                // if it is not an empty line:
                if (!(line.equals("") || line.startsWith("#"))) {
                    // fill the default map.
                    if (line.startsWith("default")) {
                        String[] parts = line.split(" ", 2);
                        String[] properties = parts[1].split(" ");
                        for (String s : properties) {
                            String[] keyAndValue = s.split(":");
                            defaultMap.put(keyAndValue[0], keyAndValue[1]);
                        }
                    }
                    if (line.startsWith("bdef")) {
                        counter = 0;
                        oneSymbolMap = new TreeMap<>();
                        String[] parts = line.split(" ", 2);
                        String[] properties = parts[1].split(" ");
                        for (String s : properties) {
                            String[] keyAndValue = s.split(":");
                            if (keyAndValue[0].equals("symbol")) {
                                symbol = keyAndValue[1];
                                if (symbol.length() > 1) {
                                    throw new IOException("symbol has more than one character.");
                                }
                            } else {
                                oneSymbolMap.put(keyAndValue[0], keyAndValue[1]);
                            }
                        }
                        // add missing properties to the map from the default map:
                        for (String key : defaultMap.keySet()) {
                            if (!oneSymbolMap.containsKey(key)) {
                                oneSymbolMap.put(key, defaultMap.get(key));
                            }
                        }
                        fillMap = new TreeMap<>();
                        // passing all the values in the map into variables:
                        for (String key : oneSymbolMap.keySet()) {
                            if (key.equals("width")) {
                                counter++;
                                width = Integer.parseInt(oneSymbolMap.get(key));
                                if (width <= 0) {
                                    throw new IOException("width is not valid.");
                                }
                            }
                            if (key.equals("height")) {
                                counter++;
                                height = Integer.parseInt(oneSymbolMap.get(key));
                                if (height <= 0) {
                                    throw new IOException("height is not valid.");
                                }
                            }
                            if (key.equals("hit_points")) {
                                counter++;
                                hitPoints = Integer.parseInt(oneSymbolMap.get(key));
                                if (hitPoints <= 0) {
                                    throw new IOException("hit points number is not valid.");
                                }
                            }
                            if (key.equals("stroke")) {
                                ColorsParser cp = new ColorsParser();
                                stroke = cp.colorFromString(oneSymbolMap.get(key));
                            }
                            if (key.equals("fill") || key.equals("fill-1")) {
                                counter++;
                                fillMap.put(0, oneSymbolMap.get(key));
                            }
                            if (key.contains("fill-") && (!key.equals("fill-1"))) {
                                Integer k = Character.getNumericValue(key.charAt(5));
                                fillMap.put(k, oneSymbolMap.get(key));
                            }
                        }
                        if (counter < 4) {
                            throw new IOException("Properties are missing.");
                        }
                        // create a block creator to this symbol:
                        CreateABlock blockCreator = new CreateABlock(width, stroke, fillMap, height, hitPoints);
                        blockCreators.put(symbol, blockCreator);
                    }
                    if (line.startsWith("sdef")) {
                        String key = null;
                        Integer value = null;
                        String[] parts = line.split(" ", 2);
                        String[] properties = parts[1].split(" ");
                        for (String s : properties) {
                            String[] keyAndValue = s.split(":");
                            if (keyAndValue[0].equals("symbol")) {
                                key = keyAndValue[1];
                                if (key.length() > 1) {
                                    throw new IOException("symbol of spacer has more than one character.");
                                }
                            } else {
                                value = Integer.parseInt(keyAndValue[1]);
                                if (value <= 0) {
                                    throw new IOException("width is not valid.");
                                }
                                spacerWidths.put(key, value);
                            }
                        }
                    }
                }
            } // end of while.
        } catch (IOException e) {
            System.out.println("cannot read the block file");
        }
        // create new blocks from symbol factory.
        BlocksFromSymbolsFactory bfsf = new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
        return bfsf;
     }
}
