package com.nafiz.swing;

import javax.swing.JProgressBar;

public class Progress extends JProgressBar {

    /**
     * @return the progressType
     */
    public ProgressType getProgressType() {
        return progressType;
    }

    /**
     * @param progressType the progressType to set
     */
    public void setProgressType(ProgressType progressType) {
        this.progressType = progressType;
        repaint();
    }

    private ProgressType progressType = ProgressType.NONE;

    public Progress() {
        setOpaque(false);
        setUI(new ProgressCircleUI(this));
    }

    public static enum ProgressType {
        NONE, DOWN_FILE, CANCEL, FILE
    }
}
