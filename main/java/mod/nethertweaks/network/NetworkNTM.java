package mod.nethertweaks.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class NetworkNTM implements IMessage
{
	// A default constructor is always required
	  public NetworkNTM(){}

	  private int toSend;
	  public NetworkNTM(int toSend) {
	    this.setToSend(toSend);
	  }

	  @Override public void toBytes(ByteBuf buf) {
	    // Writes the int into the buf
	    buf.writeInt(getToSend());
	  }

	  @Override public void fromBytes(ByteBuf buf) {
	    // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
	    setToSend(buf.readInt());
	  }

	public int getToSend() {
		return toSend;
	}

	public void setToSend(int toSend) {
		this.toSend = toSend;
	}
}
