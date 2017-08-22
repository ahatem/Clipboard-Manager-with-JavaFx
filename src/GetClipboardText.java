import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

public class GetClipboardText extends Thread {
    
    public boolean run = true;

    @Override
    public void run() {
        while(run) {
            try {
                if (!Main.allText.containsValue(getClipboardText())) {
                    try {
                        Main.allText.put(getClipboardText().replaceAll("\n", " "), getClipboardText());
                        Main.list.getItems().add(getClipboardText().replaceAll("\n", " "));
                    }catch(Exception e) {}
                }
                Thread.sleep(200);
            }catch(Exception e) {}

        }
    }

    // private boolean findItem(String text) {
    //     for (int i = 0; i <= Main.list.getItems().size() -1; i++) {
    //         if (Main.list.getItems().get(i).equals(text)) {
    //             return true;
    //         }else if(i == Main.list.getItems().size() -1) {
    //             return false;                
    //         }
    //     }
    //     return false;
    // }
    
    private String getClipboardText() throws UnsupportedFlavorException , Exception {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        return (String) c.getContents(null).getTransferData(DataFlavor.stringFlavor);
    }
        
}
