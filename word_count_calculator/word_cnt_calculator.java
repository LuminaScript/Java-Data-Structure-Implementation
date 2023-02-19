import java.io.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Arrays;


/**
 * The CommonWordFinder program finds the n most common words in a given file
 *
 * @author  Yizhen Zhang (UNI:yz4401)
 * @date 2022.12.18
 */

public class CommonWordFinder {

	/**
	 * This method parse the file and store words and counts into the map object
	 * @param bufferedReader the BufferedReader that read the file
	 * @param map the map object created by a designated data structure (hashmap, avl, or bst)
	 */
	public static void helperMethod(BufferedReader bufferedReader, MyMap<String, Integer> map) throws IOException {

		int c;
		StringBuilder str_builder= new StringBuilder();

		// use while loop to read the file

		while ((c = bufferedReader.read()) > 0) {

			// if encounter space, new line, store the word into map if there is any word
			if ((c == ' ' || c == (char) 10) && !str_builder.isEmpty()) {
				String word = str_builder.toString();
				// this word not a key in map, create one record
				if (map.get(word) == null) {
					map.put(word, 1);
				} else { // this word already a key in map, increment its count
					int prev_cnt = map.get(word);
					map.put(word, prev_cnt + 1);
				}
				str_builder.setLength(0);
			}
				// if character is letter or -(not at the start of word), append it to string-builder
			else if (c == '-' && str_builder.length() != 0)
				str_builder.append((char) c);
			else if ((c >= 'a' && c <= 'z') || (char) c == 39)
				str_builder.append((char) c);
			else if ((c >= 'A' && c <= 'Z')) {
				str_builder.append((char) Character.toLowerCase(c));
			}
		}


		// after parse all words in the file, check if we have left any words not stored into map
		if (!str_builder.isEmpty()){
			String word = str_builder.toString();
			if (map.get(word) == null) {
				map.put(word, 1);
			}else{
				int prev_cnt = map.get(word);
				map.put(word,prev_cnt+1);
			}
		}

	}

	/**
	 * Main method to count n most common words of a file
	 * Either two or three arguments, otherwise exit in error.
	 * With correct command line arguments, the program will display all unique words and their counts
	 * based on a specific order.
	 * @param args the name of the file, method specified, and the limits of display(optional)
	 */

	public static void main(String[] args) {

		// check whether the args has 2 or 3 args
		if (args.length != 2 && args.length != 3) {
			System.out.println("Usage: java CommonWordFinder <filename> <bst|avl|hash> [limit]");
			System.exit(1);
		}

		// open the file
		String file_name = args[0];
		BufferedReader objReader = null;
		try {
			objReader = new BufferedReader(new FileReader(file_name));
		} catch (FileNotFoundException e) {
			System.out.println("Error: Cannot open file '" + args[0] + "' for input.");
			System.exit(1);
		}

		// check the method
		String method = args[1];
		if (!(method.equals("bst") || method.equals("avl") || method.equals("hash"))) {
			System.out.println("Error: Invalid data structure '" + args[1] + "' received.");
			System.exit(1);
		}

		// check the limit
		int limit = 10;
		try {
			if (args.length == 3) {
				limit = Integer.parseInt(args[2]);
				if (limit <= 0) {
					System.out.println("Error: Invalid limit '" + args[2] + "' received.");
					System.exit(1);
				}
			}
		}catch(NumberFormatException e){
			System.out.println("Error: Invalid limit '" + args[2] + "' received.");
			System.exit(1);
		}

		/*
		 *   Parsing the Input File and count for unique words
		 */

		int uni_cnt; // keep count of unique words
		Pair<String, Integer>[] pairs;// An array to store all word-count pairs
		int max = 0; // keep track of the longest word's length

		// create a map object according to different method
        MyMap<String, Integer> map;
		if (method.equals("bst"))
            map = new BSTMap<>();
		else if (method.equals("avl"))
            map = new AVLTreeMap<>();
        else
            map = new MyHashMap<>();

		try {
			helperMethod(objReader, map);
		}catch(IOException e){
			System.out.println("Error: An I/O error occurred reading '" + args[0] + "'.");
			System.exit(1);
		}
		uni_cnt = map.size();

		pairs = new Pair[uni_cnt];

		// iterate through the map and store every distinct words into keys
		Iterator itr = map.iterator();
		int i = 0;
		while(itr.hasNext()) {
			if (method.equals("bst")||method.equals("avl")) {
				Node<String, Integer> tree_node = (Node<String, Integer>) itr.next();
				Pair<String, Integer> tree_pair = new Pair<>(tree_node.key, tree_node.value);
				pairs[i++] = new Pair(tree_pair.key,map.get(tree_pair.key));
			}else{
				MapEntry<String, Integer> hash_entry = (MapEntry<String, Integer>) itr.next();
				pairs[i++] = new Pair(hash_entry.key,map.get(hash_entry.key));
			}
		}


		/*
		 *   Displaying Output
		 */

		// sort the array first on counts and then by alphabets.
		Arrays.sort(pairs, new Comparator<Pair<String, Integer>>() {

			public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
				int compr = Integer.compare(o1.value, o2.value);
				if (compr > 0)
					return -1;
				if (compr < 0)
					return 1;
				return o1.key.compareTo(o2.key);
			}
		});

		int p_cnt = 1; // keep count of the words in display
		int dec_length  = (int) (Math.log10(Math.min(limit,uni_cnt)) + 1); // count numbers of digit for limit/uni_cnt

		// if user specifies a limit, determine the max-word-length within that limit

        for (int k =0; k< Math.min(limit,uni_cnt);k++){
				if (pairs[k].key.length() > max)
					max = pairs[k].key.length();
		}

		System.out.println("Total unique words: "+ uni_cnt);

		// iterate through the pairs to print out the result based on requirement
		String new_l = System.lineSeparator();
		for(int p=0; p<Math.min(limit,uni_cnt);p++){
			int indent  = max+1-pairs[p].key.length();
			String space = new String(new char[indent]).replace('\0', ' ');
			System.out.printf("%"+dec_length+"d. %s%s%d%s",p_cnt,pairs[p].key,space,pairs[p].value,new_l);
			p_cnt ++;
		}

	}


}
