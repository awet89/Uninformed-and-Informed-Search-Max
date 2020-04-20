package find_route;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class find_route {
	public static boolean citycheck(List<String> a, String c) {
		String com;
		for (int i = 0; i < a.size(); i++) {
			com = a.get(i);
			if (com.compareTo(c) == 0) {
				return false;
			}
		}
		return true;
	}

	public static int[] com(String a) {
		int count = 0;
		int[] num = new int[2];
		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) == ' ') {
				num[count] = i;
				count++;
			}
			if (count == 2) {
				break;
			}
		}
		return num;

	}

	public static int row(String[][] b, String a) {
		for (int i = 1; i < 22; i++) {
			if (b[i][0].compareTo(a) == 0) {
				return i;
			}
		}
		return 0;
	}

	public static int column(String[][] b, String a) {
		for (int i = 1; i < 22; i++) {
			if (b[0][i].compareTo(a) == 0) {
				return i;
			}
		}
		return 0;
	}

	static int fringesort(List<String> fringe, List <Integer>distance, List<Integer> level) {
		String[] f = new String[fringe.size()];
		int[] d = new int[distance.size()];
		int[] l = new int[level.size()];
		int size = distance.size();
		for (int i = 0; i < size; i++) {
			f[i] = fringe.get(i);
			d[i] = distance.get(i);
			l[i] = level.get(i);
		}
		String a;
		int b, c;
		for (int i = 0; i < distance.size(); i++) {
			for (int j = 0; j < distance.size(); j++) {
				if (d[i] < d[j]) {
					a = f[i];
					f[i] = f[j];
					f[j] = a;
					b = d[i];
					d[i] = d[j];
					d[j] = b;
					c = l[i];
					l[i] = l[j];
					l[j] = c;
				}
			}
		}
		fringe.clear();
		distance.clear();
		level.clear();
		for (int i = 0; i < size; i++) {
			fringe.add(f[i]);
			distance.add(d[i]);
			level.add(l[i]);
		}

		return 0;
	}

	public static void main(String[] args) throws IOException {

		// pass the path to the file as a parameter
		BufferedReader in = new BufferedReader(new FileReader(args[1]));
		String str;
		List<String> route = new ArrayList<String>();
		List<String> cities = new ArrayList<String>();
		int line_count = 0;
		while ((str = in.readLine()) != null) {
			route.add(str);
			if (str.compareTo("END OF INPUT") == 0) {
				break;
			}
			line_count++;
		}
		String[][] map = new String[line_count][3];
		List<String> c = new ArrayList<String>();
		String a;
		int[] ad = new int[2];

		for (int i = 0; i < line_count; i++) {
			ad = com(route.get(i));

			for (int j = 0; j < 3; j++) {
				if (j == 0) {
					a = route.get(i).substring(0, ad[j]);
					map[i][j] = a;
				}
				if (j == 1) {
					a = route.get(i).substring(ad[0] + 1, ad[j]);
					map[i][j] = a;
				}
				if (j == 2) {
					a = route.get(i).substring(ad[1] + 1, route.get(i).length());
					map[i][j] = a;
				}
			}
		}
		for (int i = 0; i < line_count; i++) {

			if (citycheck(c, map[i][0])) {
				c.add(map[i][0]);
			}
			if (citycheck(c, map[i][1])) {
				c.add(map[i][1]);
			}

		}
		String[][] adj = new String[c.size() + 1][c.size() + 1];
		for (int i = 1; i < c.size() + 1; i++) {
			adj[i][0] = c.get(i - 1);
			adj[0][i] = c.get(i - 1);
		}
		String start = args[2];
		String goal = args[3];
		List<Integer> level = new ArrayList<Integer>();
		List<Integer> distance = new ArrayList<Integer>();
		List<String> citys = new ArrayList<String>();
		List<String> fringe = new ArrayList<String>();
		List<String> visited = new ArrayList<String>();
		int expand = 0, generated = 0;
		int r, co;
		for (int i = 0; i < line_count; i++) {
			r = row(adj, map[i][0]);
			co = column(adj, map[i][1]);
			adj[r][co] = map[i][2];
			co = row(adj, map[i][0]);
			r = column(adj, map[i][1]);
			adj[r][co] = map[i][2];
		}
		fringe.add(start);
		citys.add(start);
		level.add(0);
		distance.add(0);
		expand++;
		int maxmemory =0;
		int z = 0;
		int v=0;
		int lev = 0;
		while (!fringe.isEmpty()) {
			r = row(adj, fringe.get(0));
			lev++;
			if(citycheck(visited,adj[r][0])) {
			for (int i = 1; i < c.size() + 1; i++) {
				if (adj[r][i] != null) {
					distance.add(Integer.parseInt(adj[r][i])+distance.get(0));
					fringe.add(adj[0][i]);
					level.add(lev);
					generated++;

				}
			}}
			if(maxmemory<fringe.size()) {
				maxmemory=fringe.size()-1;
			}
			if(fringe.get(0).compareTo(goal)==0) {
				break;
			}
			fringesort(fringe, distance, level);
			if(citycheck(visited,fringe.get(0))) {
			visited.add(fringe.get(0));
			}
			fringe.remove(0);
			distance.remove(0);
			level.remove(0);
			expand++;
			v++;
			z++;
		}
		if(fringe.isEmpty()) {
		System.out.printf("nodes expanded: %d\n",expand);
		System.out.printf("nodes generated: %d\n",generated);
		System.out.printf("max nodes in memory: %d\n",maxmemory);
		System.out.printf("distance: infinity\n");
		System.out.printf("route: none\n");
		}
		else {
			System.out.printf("nodes expanded: %d\n",expand);
			System.out.printf("nodes generated: %d\n",generated);
			System.out.printf("max nodes in memory: %d\n",maxmemory);
			System.out.printf("distance: %d km\n",distance.get(0));
		}
		
		
	}

}
