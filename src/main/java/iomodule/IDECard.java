package iomodule;

import com.cburch.logisim.data.Attribute;
import com.cburch.logisim.data.Attributes;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.instance.InstanceFactory;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.instance.InstanceState;
import com.cburch.logisim.instance.Port;

import javax.swing.*;

class IDECard extends InstanceFactory {
    Attribute<String> VHDPathAttr = Attributes.forString("VHD File Path");
    
    boolean isActive = false;
    String VHDPath = "";
    int pastmountpin = -1;
    
    public IDECard() {
        super("IDE Memory Card");
        setOffsetBounds(Bounds.create(-140, 0, 140, 100));
        setPorts(new Port[]{
                new Port(0, 10, Port.INPUT, 1),    //clock
                new Port(0, 20, Port.INOUT, 16),     //data
                new Port(0, 40, Port.INPUT, 1),      //read
                new Port(0, 50, Port.INPUT, 1),      //write
                new Port(0, 70, Port.OUTPUT, 1),      //IRQ
                new Port(0, 80, Port.OUTPUT, 1),      //IORDY
                new Port(0, 90, Port.INPUT, 1),      //reset
                new Port(-30, 100, Port.INPUT, 1)      //OPEN
        });

        setAttributes(
                new Attribute[]{
                        VHDPathAttr
                },
                new Object[]{
                        ""
                });
    }

    @Override
    public void paintInstance(InstancePainter painter) {
        painter.drawBounds();
        painter.drawLabel();
        //painter.drawClock(0, Direction.WEST); // draw a triangle on port 0
        painter.drawPorts();

        // Display the current counter value centered within the rectangle.
        // However, if the context says not to show state (as when generating
        // printer output), then skip this.
        /*if (painter.getShowState()) {
            if(painter.getPort(7).get(0).toIntValue() == 1)
            painter.drawLabel();
            painter.getAttributeValue(VHDPathAttr);
        }*/
    }

    @Override
    public void propagate(InstanceState state) {
        int nowmountpin = state.getPort(7).get(0).toIntValue();
        if(pastmountpin != -1) {
            switch (pastmountpin - nowmountpin) {
                case -1: //DOING
                    String VHDPath = state.getAttributeValue(VHDPathAttr);
                    if (JOptionPane.showConfirmDialog(JOptionPane.getRootFrame(), "Alert : Trying to Mount VHD Image can cause CRITICAL SECURITY PROBLEM\r\n경고 : 가상 하드디스크 이미지를 마운트하려 하고 있습니다\r\n" + VHDPath, "Alert", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 1) {
                        System.exit(-1);
                        break;
                    }
                    isActive = mount();
                    break;
                case 1: //UN-DOING
                    isActive = unmount();
                    break;
                default:
            }
        }
        pastmountpin = nowmountpin;
    }
    
    protected boolean mount() {
        getLocalPath glp = new getLocalPath();
        new Class<String>(getLocalPath.getLocalPath()).getClass().
        glp.findlocalpath();
        return true;
    }
    
    protected boolean unmount() {
        return false;
    }
}
