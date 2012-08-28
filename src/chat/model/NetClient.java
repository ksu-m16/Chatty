package chat.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class NetClient {
	public static final int s_UDP_PORT = 4445;
		    public static final int s_UDP_PORT_R = 4446;
	     
		    public static final int s_BUFFER_SIZE = 1024;
		     
		    /**
		     * InetAddress for MulticastSocket. All recipients will receive messages
		     * if they will join this group address.
		     */
		    public final InetAddress m_group = InetAddress.getByName("230.0.0.1");
		    private MulticastSocket m_socket; //UDP Socket to send messages
		     
		    private NetSender m_packSender;
		    private NetReceiver m_packReader;
		     
		    /**
		     * User nickname
		     */
		    private String m_alias;
		     
		    public NetClient(String alias) throws IOException
		    {
		        m_socket = new MulticastSocket(s_UDP_PORT);
		        m_packSender = new NetSender(this, m_socket);
		        m_packReader = new NetReceiver(this);
		        m_alias = alias;
		    }
		     
		    public void run() throws IOException
		    {
		        Thread t = new Thread(m_packReader, "reader");
		        t.setDaemon(true);
		        t.start();
		 
		        String line;
		        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		        try {
		            while(true)
		            {
		                if((line = in.readLine()) != null)
		                {
		                    m_packSender.send(m_alias + ": " + line);
		                }
		            }
		        }
		        finally
		        {
		            in.close();
		        }
		    }
		     
		    public InetAddress getGroupAddress()
		    {
		        return m_group;
		    }
		     
//		    public static void main(String[] args) throws IOException
//		    {
//		        if(args.length < 1)
//		        {
//		            System.out.println("usage: nickname is needed.");
//		            System.exit(1);
//		        }
//		         
//		        new NetClient(args[0]).run();
//		    }
}