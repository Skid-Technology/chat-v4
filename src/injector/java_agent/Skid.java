/*
 * Skid.java — the Skid Solutions splash screen.
 *
 * The Java side of the injector's front end: shown once from Agent.premain
 * (see the guard there) it draws a black window with two rotating arcs, the
 * brand name and the Discord invite, then asks whether to show it again. The
 * answer is kept in the JVM's temp directory.
 *
 * The Windows injector paints the same splash natively (src/injector/ui_win32.c).
 * No dependencies; plain Swing, Java 8.
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import javax.swing.Timer;

public final class Skid extends JWindow
        implements ActionListener, MouseListener, MouseMotionListener {

    private static final int WIDTH = 440;
    private static final int HEIGHT = 270;
    private static final int DURATION = 4000;
    private static final int TICK = 16;
    private static final int FADE_MS = 600;
    private static final float ROT_STEP = 6.5F;
    private static final float ARC_DIAMETER = 54F;
    private static final float ARC_TOP = 55F;

    private static final String BRAND = "Skid Solutions";
    private static final String DISCORD_URL = "https://discord.gg/5FB5hqJacr";
    private static final String PREF_NAME = "skid_splash_pref.txt";

    private final CountDownLatch latch;
    private final Timer timer;
    private final long startTime;
    private float angle;

    private Skid(CountDownLatch latch) {
        this.latch = latch;
        this.startTime = System.currentTimeMillis();
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.timer = new Timer(TICK, this);
    }

    /** Show the splash and wait for it to finish. Safe to call from premain. */
    public static void run() {
        String pref = readPref();
        if ("no".equals(pref)) {
            return;
        }

        CountDownLatch latch = new CountDownLatch(1);
        Skid splash = new Skid(latch);
        splash.setVisible(true);
        splash.timer.start();

        try {
            latch.await(DURATION + 3000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if ("unset".equals(pref)) {
            int answer = JOptionPane.showConfirmDialog(null,
                    "Do you want to see this splash screen again?",
                    BRAND, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            savePref(answer == JOptionPane.YES_OPTION ? "yes" : "no");
        }
    }

    @Override
    public void paint(Graphics g) {
        BufferedImage frame = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = frame.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, WIDTH, HEIGHT);

        float x = (WIDTH - ARC_DIAMETER) / 2F;
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER));
        g2.drawArc((int) x, (int) ARC_TOP, (int) ARC_DIAMETER, (int) ARC_DIAMETER,
                (int) angle, 90);
        g2.drawArc((int) x, (int) ARC_TOP, (int) ARC_DIAMETER, (int) ARC_DIAMETER,
                (int) (angle + 180F), 90);

        long elapsed = System.currentTimeMillis() - startTime;
        float fade = Math.min(1F, (float) elapsed / FADE_MS);
        g2.setColor(new Color(255, 255, 255, (int) (fade * 255F)));
        g2.setFont(new Font("Segoe UI", Font.BOLD, 20));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(BRAND, (WIDTH - fm.stringWidth(BRAND)) / 2,
                (int) (ARC_TOP + ARC_DIAMETER + 30F) + fm.getAscent());

        g2.setColor(new Color(180, 180, 180));
        g2.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        g2.drawString(DISCORD_URL, 14, 256);
        g2.dispose();

        g.drawImage(frame, 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (System.currentTimeMillis() - startTime >= DURATION) {
            timer.stop();
            dispose();
            latch.countDown();
        } else {
            angle = (angle + ROT_STEP) % 360F;
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (overLink(e)) {
            try {
                Desktop.getDesktop().browse(new URI(DISCORD_URL));
            } catch (Exception ignored) {
                /* no browser: the splash is decoration, never fatal */
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setCursor(Cursor.getPredefinedCursor(
                overLink(e) ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
    }

    private boolean overLink(MouseEvent e) {
        return e.getX() >= 14 && e.getX() <= 280 && e.getY() >= 240;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    private static File getPrefFile() {
        return new File(System.getProperty("java.io.tmpdir"), PREF_NAME);
    }

    private static String readPref() {
        File pref = getPrefFile();
        if (!pref.exists()) {
            return "unset";
        }
        try (BufferedReader in = new BufferedReader(new FileReader(pref))) {
            String line = in.readLine();
            return line != null ? line.trim().toLowerCase() : "unset";
        } catch (Exception e) {
            return "unset";
        }
    }

    private static void savePref(String value) {
        try (FileWriter out = new FileWriter(getPrefFile())) {
            out.write(value);
        } catch (Exception ignored) {
            /* the preference is a convenience; failing to store it is fine */
        }
    }
}
