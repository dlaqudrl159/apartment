package kr.co.dw.Service.AutoData.Coords;

public final class Constants {

    public static final class geocodersearchaddress {

    	public static final String EPSG = "epsg:4326";
    	public static final String PARAM_SERVICE = "?service=address";
    	public static final String PARAM_REQUEST = "&request=getCoord";
    	public static final String PARAM_FORMAT = "&format=json";
    	public static final String PARAM_crs = "&crs=";
    	public static final String PARAM_key = "&key=";
    	public static final String PARAM_type = "&type=";
        
    }	
    
    public static final class getparcel {
    	
    	public static final String SEARCHType = "parcel";
    	
    }
    
    public static final class getroadname {
    	
    	public static final String SEARCHType = "road";
    	
    }
    
}
