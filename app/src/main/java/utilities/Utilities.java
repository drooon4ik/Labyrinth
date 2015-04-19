package utilities;

import com.labyrinth.myapplication.AABB;
import com.labyrinth.myapplication.Vec2;
import com.labyrinth.myapplication.Vector;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Utilities {

    //36 as specified in f
    public static ModelData loadModelExt(InputStream fileInputStream) {
        ArrayBuilder<Float> vBuilder = new ArrayBuilder<>();
        ArrayBuilder<Float> vtBuilder = new ArrayBuilder<>();
        ArrayBuilder<String> fBuilder = new ArrayBuilder<>();
        ArrayBuilder<Float> vnBuilder = new ArrayBuilder<>();

        float[] v;
        float[] vn;
        float[] vt;

        String line;
        String token;
        BufferedReader br = null;

        StringTokenizer tokenizer;
        try {
            br = new BufferedReader(new InputStreamReader(fileInputStream));
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.startsWith("vt")) {
                    tokenizer = new StringTokenizer(line);
                    int columnCounter = 0;
                    while(tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken(" ");
                        try {
                            vtBuilder.add(Float.parseFloat(token), columnCounter);
                            columnCounter++;
                        } catch(NumberFormatException e) {
                            continue;
                        }
                    }
                    vtBuilder.addRow();
                    continue;
                }
                if(line.startsWith("vn")) {
                    tokenizer = new StringTokenizer(line);
                    int columnCounter = 0;
                    while(tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken(" ");
                        try {
                            vnBuilder.add(Float.parseFloat(token), columnCounter);
                            columnCounter++;
                        } catch(NumberFormatException ex) {
                            continue;
                        }
                    }
                    vnBuilder.addRow();
                    continue;
                }
                if (line.startsWith("v")) {
                    tokenizer = new StringTokenizer(line);
                    int columnCounter = 0;
                    while(tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken(" ");
                        try {
                            vBuilder.add(Float.parseFloat(token), columnCounter);
                            columnCounter++;
                        } catch(NumberFormatException e) {
                            continue;
                        }
                    }
                    vBuilder.addRow();
                    continue;
                }
                if (line.startsWith("f")) {
                    tokenizer = new StringTokenizer(line);
                    int columnCounter = 0;
                    while(tokenizer.hasMoreTokens()) {
                        token = tokenizer.nextToken(" ");
                        if(token.equals("f")) {
                            continue;
                        }
                        fBuilder.add(token, columnCounter);
                        columnCounter++;
                    }
                    fBuilder.addRow();
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(br);
        }

        Float[][] vBuilt = vBuilder.getFormedArray();
        Float[][] vtBuilt = vtBuilder.getFormedArray();
        Float[][] vnBuilt = vnBuilder.getFormedArray();
        String[][] fBuilt = fBuilder.getFormedArray();

        int index = -1;
        int vCounter = 0;
        int vtCounter = 0;
        int vnCounter = 0;

        int size = fBuilt.length * fBuilt[0].length * 3;

        v = new float[size];
        vt = new float[size];
        vn = new float[size];

        System.out.println(v.length);
        System.out.println(vt.length);

        for (int i = 0; i < fBuilt.length; i++) {
            for (int j = 0; j < fBuilt[i].length; j++) {
                tokenizer = new StringTokenizer(fBuilt[i][j]);
                if(tokenizer.hasMoreTokens()) {
                    index = Integer.parseInt(tokenizer.nextToken("/"));
                    for (int k = 0; k < 3; k++) {
                        v[vCounter++] = vBuilt[index - 1][k];
                    }
                }
                if(tokenizer.hasMoreTokens()) {
                    index = Integer.parseInt(tokenizer.nextToken("/"));
                    for (int k = 0; k < 3; k++) {
                        vt[vtCounter++] = vtBuilt[index - 1][k];
                    }
                }
                if(tokenizer.hasMoreTokens()) {
                    index = Integer.parseInt(tokenizer.nextToken("/"));
                    for (int k = 0; k < 3; k++) {
                        vn[vnCounter++] = vnBuilt[index - 1][k];
                    }
                }
            }
        }

        ModelData md = new ModelData();
        md.v = v.clone();
        md.vt = vt.clone();
        md.vn = vn.clone();

        return md;
    }

	public static float[] loadModel(InputStream fileInputStream) {
		float[] model;
		Float[][] v;
		Float[][] vt;
		
		String[][] f;

        InputStream is = fileInputStream;
        ArrayBuilder<Float> vBuilder = new ArrayBuilder<>();
        ArrayBuilder<Float> vtBuilder = new ArrayBuilder<>();
        ArrayBuilder<String> fBuilder = new ArrayBuilder<>();
		
		String line;
		BufferedReader br = null;
		
		StringTokenizer tokenizer;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#")) {
					continue;
				}
				if (line.startsWith("vt")) {
					tokenizer = new StringTokenizer(line);
					int columnCounter = 0;
					while(tokenizer.hasMoreTokens()) {
						String str = tokenizer.nextToken(" ");
						try {
							vtBuilder.add(Float.parseFloat(str), columnCounter);
							columnCounter++;
						} catch(NumberFormatException e) {
							continue;
						}
					}
					vtBuilder.addRow();
					continue;
				}
				if (line.startsWith("v")) {
					tokenizer = new StringTokenizer(line);
					int columnCounter = 0;
					while(tokenizer.hasMoreTokens()) {
						String str = tokenizer.nextToken(" ");
						try {
							vBuilder.add(Float.parseFloat(str), columnCounter);
							columnCounter++;
						} catch(NumberFormatException e) {
							continue;
						}
					}
					vBuilder.addRow();
					continue;
				}
				if (line.startsWith("f")) {
					tokenizer = new StringTokenizer(line);
					int columnCounter = 0;
					while(tokenizer.hasMoreTokens()) {
						String str = tokenizer.nextToken(" ");
						if(str.equals("f")) {
							continue;
						}
						fBuilder.add(str, columnCounter);
						columnCounter++;
					}
					fBuilder.addRow();
					continue;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close(br);
		}
		
		v = vBuilder.getFormedArray();
		vt = vtBuilder.getFormedArray();
		f = fBuilder.getFormedArray();
		
		model = new float[f[0].length * 6 * f.length];
		int index = -1;
		int counter = 0;
		
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[i].length; j++) {
				tokenizer = new StringTokenizer(f[i][j]);
				if(tokenizer.hasMoreTokens()) {
					index = Integer.parseInt(tokenizer.nextToken("/"));
					for (int k = 0; k < 3; k++) {
						model[counter++] = v[index - 1][k];
					}
				}
				if(tokenizer.hasMoreTokens()) {
					index = Integer.parseInt(tokenizer.nextToken());
					for (int k = 0; k < 3; k++) {
						model[counter++] = vt[index - 1][k];
					}
				}
			}
		}
		
		return model;
	}

    public static List<AABB> loadCollisionShit(InputStream stream) {
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        ArrayBuilder<Float> vbuilder = new ArrayBuilder<>();
        ArrayBuilder<Integer> fbuilder = new ArrayBuilder<>();

        try {
            while((line = reader.readLine()) != null) {
                if(line.startsWith("v")) {
                    int columnCounter = 0;
                    for(String token : line.split(" ")) {
                        try {
                            vbuilder.add(Float.parseFloat(token), columnCounter++);
                        } catch(NumberFormatException e) {
                            continue;
                        }
                    }
                    vbuilder.addRow();
                }
                if(line.startsWith("f")) {
                    int columnCounter = 0;
                    for(String token : line.split(" ")) {
                        try {
                            fbuilder.add(Integer.parseInt(token), columnCounter++);
                        } catch(NumberFormatException e) {
                            continue;
                        }
                    }
                    fbuilder.addRow();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Float[][] v = vbuilder.getFormedArray();
        Integer[][] f = fbuilder.getFormedArray();

        List<AABB> list = new ArrayList<>();

        for (int i = 0; i < f.length; i++) {
            Vector[] vectors = new Vector[3];
            int counter = 0;
            for (int j = 0; j < f[i].length; j++) {
                vectors[counter++] = new Vec2(unwrap(v[f[i][j] - 1]));
            }
            AABB aabb = new AABB(vectors);
            //aabb.vertices = vectors;
            list.add(aabb);
            counter = 0;
        }
        System.out.println(list);
        return list;

    }

    private static void fillArray(float[] to, Float[] from, int startIndex) {
        for (int i = 0; i < from.length; i++) {
            to[startIndex++] = from[i];
        }
    }

    private static float[] unwrap(Float[] array) {
        float[] result = new float[array.length - 1];
        result[0] = array[0];
		result[1] = array[2];
        return result;
    }

	private static void close(BufferedReader br) {
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
