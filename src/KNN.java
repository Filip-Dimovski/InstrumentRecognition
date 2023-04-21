

import java.util.*;

class KNN
{
	// obuchuvachkoto mnozhestvo
	static double[][] instances = {
		{83.22,280,2.936,444.758},{76.49,247,2.867,591.83},{69.65,140,2.971,962.998},{84.01,123,2.791,551.128},
		{83.76,165,2.835,112299.516},{72.87,132,2.805,1202.982},{87.47,189,3.214,112135.6},{67.47,145,2.724,150.918},
		{79.64,90,2.91,112643.61},{80.16,124,2.793,479.182},{74.82,158,2350.4,628.98866},{80.74,138,2962.2,458.76266},
		{71.23,351,2909.4,497.674},{77.93,423,2846.2,366.402},{77.51,225,2893.6,252.23266},{70.92,224,3021.6,703.22666},
		{70.77,161,2332.8,670.846},{72.89,144,2702.8,710.10066},{79.28,170,2749.6,524.52466},{63.35,162,2423.4,377.85666},
		{86.69,377,3062.4,529.682},{81.47,231,3006.4,406.90508},{66.66,477,2965.6,69.314},{73.86,418,2878,430.18108},
		{81.17,458,2843,2667.878},{79.97,298,2971.8,389.16708},{75.82,327,2916,593.34908},{85.9,482,2457.6,350.61908},
		{68.66,423,2993.6,779.342},{73.87,310,2528,821.834},{81.58,169,2910,217.55},{83.34,163,2889.2,823.966},
		{70.79,153,2911.6,1287.564},{78.22,284,2608,509.6},{76.57,159,2419,369.32},{75.81,386,2967.6,492.206},
		{57.5,303,3172.6,439.434},{83.1,147,2786.6,308.54},{60.91,285,2692.2,132.62},{74.19,111,2484.6,933.002}

	};


	/**
	 * Returns the majority value in an array of strings
	 * majority value is the most frequent value (the mode)
	 * handles multiple majority values (ties broken at random)
	 *
	 * @param  array an array of strings
	 * @return  the most frequent string in the array
	 */ 
	//se povikuva so najbliskite 7 sosedi
	private static String findMajorityClass(String[] array)
	{
		//add the String array to a HashSet to get unique String values
		Set<String> h = new HashSet<String>(Arrays.asList(array));
		
		//convert the HashSet back to array
		String[] uniqueValues = h.toArray(new String[0]);
		//counts for unique strings
		int[] counts = new int[uniqueValues.length];
		// loop thru unique strings and count how many times they appear in origianl array   
		for (int i = 0; i < uniqueValues.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if(array[j].equals(uniqueValues[i])){
					counts[i]++;
				}
			}        
		}

		for (int i = 0; i < uniqueValues.length; i++)
			System.out.println(uniqueValues[i]);
		for (int i = 0; i < counts.length; i++)
			System.out.println(counts[i]);

       //Maksimalniot broj na pojavuvanje na instrumentot
		int max = counts[0];
		for (int counter = 1; counter < counts.length; counter++) {
			if (counts[counter] > max) {
				max = counts[counter];
			}
		}
		System.out.println("Најголемиот број на појави на инструмент е: "+max);

		// how many times max appears
		//we know that max will appear at least once in counts
		//so the value of freq will be 1 at minimum after this loop
		//Ja barame frekventnosta na maksimumot.Ako e eden togas ima samo edna klasa koja ima najmnogu sosedi so pesnata.
		//Dokolku freq>1 toa znachi deka povekje klasi ednakvo se pojavuvaat kako sosedi na nepoznatata pesna
		int freq = 0;
		for (int counter = 0; counter < counts.length; counter++) {
			if (counts[counter] == max) {
				freq++;
			}
		}

		//Ako samo edna klasa se pojavuva maksimalen broj na pati
		// Vo indeks se naogja pesnata koja se naogja od najfrekventnata klasa i koja e najblisku do  nepoznatata pesna
		int index = -1;
		if(freq==1){
			for (int counter = 0; counter < counts.length; counter++) {
				if (counts[counter] == max) {
					index = counter;
					break;
				}
			}
		System.out.println("Една главна класа, најблискиот податок од таа класа е со индекс: "+index);
			return uniqueValues[index];
		} else{//dokolku imame povekje istofrekventni klasi
			int[] ix = new int[freq];//array of indices of modes
			System.out.println("Број на главни класи: "+freq+" classes");
			int ixi = 0;
			for (int counter = 0; counter < counts.length; counter++) {
				if (counts[counter] == max) {
					//se zachuvuva indeksot so maksimalna vrednost
					ix[ixi] = counter;
					
					ixi++;
				}
			}

			for (int counter = 0; counter < ix.length; counter++)         
				System.out.println("Индекс на класата: "+ix[counter]);       

			//Se bira random
			Random generator = new Random();        
		
			int rIndex = generator.nextInt(ix.length);
			System.out.println("Случаен индекс: "+rIndex);
			int nIndex = ix[rIndex];
	
			return uniqueValues[nIndex];
		}

	}


	/**
	 * Returns the mean (average) of values in an array of doubless
	 * sums elements and then divides the sum by num of elements
	 *
	 * @param  array an array of doubles
	 * @return  the mean
	 */ 
	private static double meanOfArray(double[] m) {
		double sum = 0.0;
		for (int j = 0; j < m.length; j++){
			sum += m[j];
		}
		return sum/m.length;
	}



	public static void main(String args[]){ 
        //7-te najbliski vektori do nepoznatiot podatok shto sakame da go klasificirame
		int k = 7;
		//Lista za zachuvuvanje na instrumentite i nivnite podatoci
		List<Instrument> instrumentList = new ArrayList<Instrument>();
		//Lista  kade se chuvaat rezultatite
		List<Result> resultList = new ArrayList<Result>();
		// Vo InstrumentList se dodavaat vrednostite od instances i imeto na instrumentot
		String imeKlasa="";
		for(int i=0;i<40;i++)
		{
			if(i>=0 && i<10) imeKlasa="Жичани";
			if(i>=10 && i<20) imeKlasa="Клавијатура";
			if(i>=20 && i<30) imeKlasa="Дувачки";
			if(i>=30 && i<40) imeKlasa="Ударни";
			instrumentList.add(new Instrument(instances[i],imeKlasa));	
			
		}
		
		
		//Pesnata shto sakame da ja klasificirame
		double[] query = {72.65,164,2477.6,474.876};
		//naogja evklidovo rastojanie pomegju nepoznata pesna i pomegju site pesni
		for(Instrument instrument : instrumentList){
			double dist = 0.0;  
			//Evklidovo rastojanie
			for(int j = 0; j < instrument.instrumentAttributes.length; j++){    	     
				dist += Math.pow(instrument.instrumentAttributes[j] - query[j], 2) ;
				//System.out.print(instrument.instrumentAttributes[j]+" ");    	     
			}
			double distance = Math.sqrt( dist );
			//vo resultList se zachuvuva rastojanieto i imeto na instrumentot
			resultList.add(new Result(distance,instrument.instrumentName));
			//System.out.println(distance+" "+instrument.instrumentName);
		} 

		
		//gi sortira podatocite spored DistanceComparator
		Collections.sort(resultList, new DistanceComparator());
		String[] ss = new String[k];
		// gi pechati prvite 7 instrumenti spored nivnata oddalechenost
		for(int x = 0; x < k; x++){
			System.out.println(resultList.get(x).instrumentName+ " .... " + resultList.get(x).distance);
			// gi zema iminjata na klasite koi se najblisku do pesnata 
			ss[x] = resultList.get(x).instrumentName;
		}
		//zema koja klasa ja imame najmnogu
		String majClass = findMajorityClass(ss);
		System.out.println("Класата на непознатата песна е: "+majClass);                
	}//end main  

	
	// Instrumentot + negovite karakteristiki i imeto
	static class Instrument {	
		double[] instrumentAttributes;
		String instrumentName;
		public Instrument(double[] instrumentAttributes, String instrumentName){
			this.instrumentName = instrumentName;
			this.instrumentAttributes = instrumentAttributes;	    	    
		}
	}
	//klasa kade shto se chuvaat oddalechenosta i imeto na instrumentot
	static class Result {	
		double distance;
		String instrumentName;
		public Result(double distance, String instrumentName){
			this.instrumentName = instrumentName;
			this.distance = distance;	    	    
		}
	}
	//gi sporeduva oddalechenosta pomegju 2 rezultati
	static class DistanceComparator implements Comparator<Result> {
		@Override
		public int compare(Result a, Result b) {
			return a.distance < b.distance ? -1 : a.distance == b.distance ? 0 : 1;
		}
	}

}

