package code4as.com.zappos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONException;

public class Zappos_Git_Giver {
	
	public static void main(String args[]) throws NumberFormatException, IOException{
	//System.out.println("started execution");
	int num_gifts = 0;
	int low;
	int high;
	int mid = 0;
	double desired_price = 0.0;	
	double look_up_price = 0.0;
	double nearest_price; 
	final String api_key = "52ddafbe3ee659bad97fcce7c53592916a6bfd73"; 
	Connector conn = new Connector();
	BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
	//do{
		System.out.println("Hi there..please enter the number of gifts...");
		num_gifts = Integer.parseInt(br.readLine());
		System.out.println("Also..please enter desired total for these gifts ...");
		desired_price = Integer.parseInt(br.readLine());
		look_up_price = desired_price / num_gifts;
		JSONArray j = conn.getProductInfo("", new Double(look_up_price).toString(), "52ddafbe3ee659bad97fcce7c53592916a6bfd73");
		System.out.println("succ returned");
		if(j == null) System.out.println("Bad connection...");
		else {
		int return_size = j.length();
		if(return_size == 0){
			
			JSONArray price_name = conn.getPrices("", new Double(look_up_price).toString(), api_key);
			List<Double> price_list = new ArrayList<Double>();
					
				try {
					for (int i=0;i<price_name.length();i++){
						if(price_name.getJSONObject(i).getDouble("name") <= desired_price )
							price_list.add( price_name.getJSONObject(i).getDouble("name"));
							
					}
					low = 0;high=price_list.size() - 1;
					//find nearest element in our set...
					System.out.println("finding closest match...with look_up_price " + look_up_price);
					mid = (low + high) /2;
					while(low <= high){
					
						System.out.println(price_list.get(mid));
						if(price_list.get(mid)== look_up_price) ; 
						else if(look_up_price < price_list.get(mid) ) high = mid - 1;
						else low = mid + 1;
						mid =  (low + high) /2 ; 
					}
					//find closest match i.e either mid or mid + 1
					if((price_list.get(mid) - look_up_price) > (price_list.get(mid + 1) - look_up_price)) nearest_price = price_list.get(mid); 
					else nearest_price = price_list.get(mid + 1); 
					System.out.println("closest is" + nearest_price);
					
					if(nearest_price == 0){
						//impossible.....
					}
					else
						{j = conn.getProductInfo("", new Double(nearest_price).toString(), api_key);
						 System.out.println(j.length());
						//two possible cases : returned elements less than num of gifts or more
						 if(j.length() > num_gifts){
							 print_large_combinations(j,num_gifts);
							 
						 }
						 else if(j.length() < num_gifts){
							 print_small_combinations(j,num_gifts);
							 
						 }
						}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			
		}
	//	}	
		}
		
		
		
	}

	private static void print_small_combinations(JSONArray j, int num_gifts) {
		System.out.println("===========================================================================================================\n");
		
		
		int c = 0;
		while(c < num_gifts){
			for (int i =0 ; i < j.length() && c < num_gifts ; i++){
				String data;
				try {
					data = j.getJSONObject(i).toString();
					System.out.println(data);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				c++;
			}
			System.out.println("===========================================================================================================\n");
		}
		
	}

	private static List<Set<Object>> print_large_combinations(JSONArray j, int num_gifts) {
		//print all possible num_gifts combinations present in j
		List<Set<Object>> res = new ArrayList();
		recursive_combination(j,num_gifts, 0, new HashSet<Object>(), res);
		Iterator<Set<Object>> itr = res.iterator();
		while(itr.hasNext()){
			System.out.println("=======================================Options for our valued customers=====================================\n");
			Iterator<Object> itr1 = (Iterator<Object>)itr.next();
			while(itr1.hasNext()){
				Object data = itr1.next();
				data.toString();
				
			}
			System.out.println("=============================================================================================================\n");
		}
		return res;
		
	}

	public static void recursive_combination(JSONArray j, int k,
			int index, HashSet<Object> current,List<Set<Object>> solution) {
			if(k == current.size()){
				solution.add(new HashSet<>(current));
				return;
			}
			if(index == j.length()) return ;
			Object x;
			try {
				x = j.get(index);
				current.add(x);
				recursive_combination(j,k,index+1,current,solution);
				current.remove(x);
				recursive_combination(j,k,index+1,current,solution);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
	}
	
	
}
