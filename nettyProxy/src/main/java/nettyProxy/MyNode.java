package nettyProxy;

public class MyNode {
    public String REMOTE_HOST;
    public int REMOTE_PORT;
    
    public MyNode(String REMOTE_HOST,int REMOTE_PORT) {
		this.REMOTE_HOST = REMOTE_HOST;
		this.REMOTE_PORT = REMOTE_PORT;
	}
    
    @Override
    public String toString() {
    	return REMOTE_HOST+ " "+Integer.toString(REMOTE_PORT);
    }
}
