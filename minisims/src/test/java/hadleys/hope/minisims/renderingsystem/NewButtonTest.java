package hadleys.hope.minisims.renderingsystem;

import hadleys.hope.minisims.test.utils.ApplicationThread;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NewButtonTest {
    
    private FrameFixture window;
    
    private ApplicationThread appthread;
    
    @Before
    public void init() {
        this.appthread = new ApplicationThread();
        (new Thread(this.appthread)).start();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            // Do nothing sleep failed
        }
        
        Window frame = GuiActionRunner.execute(new GuiQuery<Window>() {
            
            @Override
            protected Window executeInEDT() {
              return RenderingManager.get().getWindow();
            }
        });
        
        window = new FrameFixture(frame);
        window.show();
    }
    
    @After
    public void tearDown() {
        this.window.cleanUp();
        this.appthread.shutdownApp();
      
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            // Do nothing sleep failed
        }
    }
    
    @Test
    public void shouldZeroCurrentPointsCounter() {
        this.window.textBox("powerField").enterText("10");
        this.window.textBox("angleField").enterText("10");
        this.window.button("hitButton").click();
        
        this.window.label("currentPoints").requireText("1");
        
        this.window.button("newGameButton").click();
        
        this.window.label("currentPoints").requireText("0");
    }
}
