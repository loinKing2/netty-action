package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import org.netty.part06.AbsIntegerEncoder;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
public class AbsIntegerEncoderTest {

    @Test
    public void testEncoded(){
        ByteBuf buf = Unpooled.buffer();
        for (int i = 1; i < 10; i++) {
            buf.writeInt(i * -1);
        }
        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());

        for (int i = 1; i < 10; i++) {
            assert i == (int)channel.readOutbound();
        }
        assertNull(channel.readOutbound());
    }

}
