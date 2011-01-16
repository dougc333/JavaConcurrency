
// just a resizing array for holding the input
// fields are public for simplicity
// note array may not be full; see data_size field

public class CensusData {
	public static final int INITIAL_SIZE = 100;
	public CensusGroup[] data;
	public int data_size;
	
	public CensusData() {
		data = new CensusGroup[INITIAL_SIZE];
		data_size = 0;
	}
	
	public void add(int population, float latitude, float longitude) {
		if(data_size == data.length) { // resize
			CensusGroup[] new_data = new CensusGroup[data.length*2];
			for(int i=0; i < data.length; ++i)
				new_data[i] = data[i];
			data = new_data;
		}
		CensusGroup g = new CensusGroup(population,latitude,longitude); 
		data[data_size++] = g;
	}
}
