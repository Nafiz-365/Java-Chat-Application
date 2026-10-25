package com.nafiz.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;

public final class ModernIcons {

    @FunctionalInterface
    public interface IconPainter {
        void paint(Graphics2D g2, int width, int height);
    }

    private static class VectorIcon implements Icon {
        private final int width;
        private final int height;
        private final IconPainter painter;

        VectorIcon(int width, int height, IconPainter painter) {
            this.width = width;
            this.height = height;
            this.painter = painter;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.translate(x, y);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            painter.paint(g2, width, height);
            g2.dispose();
        }

        @Override public int getIconWidth()  { return width; }
        @Override public int getIconHeight() { return height; }
    }

    private ModernIcons() {}

    // ─── Chat Bubble ─────────────────────────────────────────────────────────
    public static Icon chat(Color color, boolean filled, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            float pad = size * 0.12f;
            float bw = w - pad * 2;
            float bh = h - pad * 2 - (size * 0.14f);
            float arc = size * 0.35f;
            Path2D path = new Path2D.Float();
            path.append(new RoundRectangle2D.Float(pad, pad, bw, bh, arc, arc), false);
            path.moveTo(pad + bw * 0.2f, pad + bh - 1);
            path.lineTo(pad + bw * 0.08f, h - pad * 0.6f);
            path.lineTo(pad + bw * 0.35f, pad + bh - 1);
            path.closePath();
            if (filled) {
                g2.fill(path);
                g2.setColor(Color.WHITE);
                float dotR = size * 0.07f, cy = pad + bh * 0.5f, cx = pad + bw * 0.5f, sp = size * 0.16f;
                g2.fill(new Ellipse2D.Float(cx - sp - dotR, cy - dotR, dotR * 2, dotR * 2));
                g2.fill(new Ellipse2D.Float(cx - dotR,      cy - dotR, dotR * 2, dotR * 2));
                g2.fill(new Ellipse2D.Float(cx + sp - dotR, cy - dotR, dotR * 2, dotR * 2));
            } else {
                g2.setStroke(new BasicStroke(size * 0.085f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.draw(path);
                float dotR = size * 0.05f, cy = pad + bh * 0.5f, cx = pad + bw * 0.5f, sp = size * 0.16f;
                g2.fill(new Ellipse2D.Float(cx - sp - dotR, cy - dotR, dotR * 2, dotR * 2));
                g2.fill(new Ellipse2D.Float(cx - dotR,      cy - dotR, dotR * 2, dotR * 2));
                g2.fill(new Ellipse2D.Float(cx + sp - dotR, cy - dotR, dotR * 2, dotR * 2));
            }
        });
    }

    // ─── Group / Users ────────────────────────────────────────────────────────
    public static Icon group(Color color, boolean filled, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            float pad = size * 0.14f, strokeW = size * 0.085f;
            g2.setStroke(new BasicStroke(strokeW, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            float headR = size * 0.16f, headX = w * 0.45f - headR, headY = pad + size * 0.02f;
            if (filled) {
                g2.fill(new Ellipse2D.Float(headX, headY, headR * 2, headR * 2));
                Path2D body = new Path2D.Float();
                body.moveTo(w * 0.18f, h - pad);
                body.curveTo(w * 0.18f, h * 0.62f, w * 0.72f, h * 0.62f, w * 0.72f, h - pad);
                body.closePath();
                g2.fill(body);
                float sHeadR = headR * 0.8f;
                g2.fill(new Ellipse2D.Float(w * 0.72f - sHeadR, headY + size * 0.05f, sHeadR * 2, sHeadR * 2));
                Path2D sBody = new Path2D.Float();
                sBody.moveTo(w * 0.65f, h * 0.68f);
                sBody.curveTo(w * 0.75f, h * 0.68f, w * 0.88f, h * 0.76f, w * 0.88f, h - pad);
                g2.draw(sBody);
            } else {
                g2.draw(new Ellipse2D.Float(headX, headY, headR * 2, headR * 2));
                Path2D body = new Path2D.Float();
                body.moveTo(w * 0.20f, h - pad);
                body.curveTo(w * 0.20f, h * 0.62f, w * 0.70f, h * 0.62f, w * 0.70f, h - pad);
                g2.draw(body);
                float sHeadR = headR * 0.75f;
                g2.draw(new Ellipse2D.Float(w * 0.73f - sHeadR, headY + size * 0.06f, sHeadR * 2, sHeadR * 2));
                Path2D sBody = new Path2D.Float();
                sBody.moveTo(w * 0.65f, h * 0.68f);
                sBody.curveTo(w * 0.76f, h * 0.68f, w * 0.88f, h * 0.78f, w * 0.88f, h - pad);
                g2.draw(sBody);
            }
        });
    }

    // ─── Archive / Box ────────────────────────────────────────────────────────
    public static Icon archive(Color color, boolean filled, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            float pad = size * 0.16f, strokeW = size * 0.085f;
            g2.setStroke(new BasicStroke(strokeW, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            float boxY = pad + size * 0.22f, boxH = h - pad - boxY, boxW = w - pad * 2;
            if (filled) {
                g2.fill(new RoundRectangle2D.Float(pad, pad, boxW, size * 0.22f, 4, 4));
                g2.fill(new RoundRectangle2D.Float(pad + size * 0.04f, boxY, boxW - size * 0.08f, boxH, 4, 4));
                g2.setColor(Color.WHITE);
                g2.fill(new RoundRectangle2D.Float(w * 0.38f, boxY + boxH * 0.25f, w * 0.24f, size * 0.08f, 2, 2));
            } else {
                g2.draw(new RoundRectangle2D.Float(pad, pad, boxW, size * 0.22f, 4, 4));
                Path2D body = new Path2D.Float();
                body.moveTo(pad + size * 0.04f, pad + size * 0.22f);
                body.lineTo(pad + size * 0.04f, h - pad);
                body.lineTo(pad + boxW - size * 0.04f, h - pad);
                body.lineTo(pad + boxW - size * 0.04f, pad + size * 0.22f);
                g2.draw(body);
                g2.draw(new RoundRectangle2D.Float(w * 0.38f, boxY + boxH * 0.25f, w * 0.24f, size * 0.06f, 2, 2));
            }
        });
    }

    // ─── Paper Plane Send ────────────────────────────────────────────────────
    public static Icon send(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            float pad = size * 0.16f;
            Path2D plane = new Path2D.Float();
            plane.moveTo(pad, pad);
            plane.lineTo(w - pad, h * 0.5f);
            plane.lineTo(pad, h - pad);
            plane.lineTo(pad + size * 0.2f, h * 0.5f);
            plane.closePath();
            g2.fill(plane);
        });
    }

    // ─── Back Arrow ──────────────────────────────────────────────────────────
    public static Icon back(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(size * 0.12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D arrow = new Path2D.Float();
            arrow.moveTo(w * 0.65f, h * 0.22f);
            arrow.lineTo(w * 0.32f, h * 0.50f);
            arrow.lineTo(w * 0.65f, h * 0.78f);
            g2.draw(arrow);
        });
    }

    // ─── Close / X ───────────────────────────────────────────────────────────
    public static Icon close(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(size * 0.12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            float pad = size * 0.22f;
            g2.drawLine((int) pad, (int) pad, (int) (w - pad), (int) (h - pad));
            g2.drawLine((int) (w - pad), (int) pad, (int) pad, (int) (h - pad));
        });
    }

    // ─── Search / Magnifier ──────────────────────────────────────────────────
    public static Icon search(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(size * 0.10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            float r = size * 0.28f, cx = w * 0.40f, cy = h * 0.40f;
            g2.draw(new Ellipse2D.Float(cx - r, cy - r, r * 2, r * 2));
            float lx1 = cx + r * 0.70f, ly1 = cy + r * 0.70f;
            g2.drawLine((int) lx1, (int) ly1, (int) (w * 0.82f), (int) (h * 0.82f));
        });
    }

    // ─── Single Tick (sent) ──────────────────────────────────────────────────
    public static Icon tick(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(size * 0.14f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D check = new Path2D.Float();
            check.moveTo(w * 0.18f, h * 0.52f);
            check.lineTo(w * 0.42f, h * 0.75f);
            check.lineTo(w * 0.82f, h * 0.28f);
            g2.draw(check);
        });
    }

    // ─── Double Tick (seen) ───────────────────────────────────────────────────
    public static Icon doubleTick(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(size * 0.12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D c1 = new Path2D.Float();
            c1.moveTo(w * 0.08f, h * 0.54f);
            c1.lineTo(w * 0.30f, h * 0.76f);
            c1.lineTo(w * 0.62f, h * 0.30f);
            g2.draw(c1);
            Path2D c2 = new Path2D.Float();
            c2.moveTo(w * 0.28f, h * 0.54f);
            c2.lineTo(w * 0.50f, h * 0.76f);
            c2.lineTo(w * 0.90f, h * 0.28f);
            g2.draw(c2);
        });
    }

    // ─── Save / Download ─────────────────────────────────────────────────────
    public static Icon save(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            float pad = size * 0.14f;
            g2.setStroke(new BasicStroke(size * 0.10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine((int) (w * 0.5f), (int) pad, (int) (w * 0.5f), (int) (h * 0.72f));
            Path2D arr = new Path2D.Float();
            arr.moveTo(w * 0.28f, h * 0.52f);
            arr.lineTo(w * 0.50f, h * 0.76f);
            arr.lineTo(w * 0.72f, h * 0.52f);
            g2.draw(arr);
            g2.drawLine((int) pad, (int) (h - pad), (int) (w - pad), (int) (h - pad));
        });
    }

    // ─── File / Document ────────────────────────────────────────────────────
    public static Icon file(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            float pad = size * 0.14f;
            g2.setStroke(new BasicStroke(size * 0.09f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            float fw = w - pad * 2, fh = h - pad * 2, fold = fw * 0.32f;
            Path2D doc = new Path2D.Float();
            doc.moveTo(pad, pad);
            doc.lineTo(pad + fw - fold, pad);
            doc.lineTo(pad + fw, pad + fold);
            doc.lineTo(pad + fw, pad + fh);
            doc.lineTo(pad, pad + fh);
            doc.closePath();
            g2.draw(doc);
            g2.drawLine((int) (pad + fw - fold), (int) pad, (int) (pad + fw - fold), (int) (pad + fold));
            g2.drawLine((int) (pad + fw - fold), (int) (pad + fold), (int) (pad + fw), (int) (pad + fold));
            float ly = pad + fh * 0.50f, lx1 = pad + fw * 0.16f, lx2 = pad + fw * 0.78f;
            g2.drawLine((int) lx1, (int) ly, (int) lx2, (int) ly);
            g2.drawLine((int) lx1, (int) (ly + fh * 0.18f), (int) lx2, (int) (ly + fh * 0.18f));
        });
    }

    // ─── Image / Photo ───────────────────────────────────────────────────────
    public static Icon image(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            float pad = size * 0.14f;
            g2.setStroke(new BasicStroke(size * 0.09f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            float fw = w - pad * 2, fh = h - pad * 2;
            g2.draw(new RoundRectangle2D.Float(pad, pad, fw, fh, 6, 6));
            float sr = fw * 0.12f, sx = pad + fw * 0.28f, sy = pad + fh * 0.30f;
            g2.fill(new Ellipse2D.Float(sx - sr, sy - sr, sr * 2, sr * 2));
            Path2D mtn = new Path2D.Float();
            mtn.moveTo(pad, pad + fh * 0.80f);
            mtn.lineTo(pad + fw * 0.40f, pad + fh * 0.44f);
            mtn.lineTo(pad + fw * 0.60f, pad + fh * 0.64f);
            mtn.lineTo(pad + fw * 0.80f, pad + fh * 0.50f);
            mtn.lineTo(pad + fw, pad + fh);
            g2.draw(mtn);
        });
    }

    // ─── Attachment / Paperclip ───────────────────────────────────────────────
    public static Icon attachment(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(size * 0.10f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            float cx = w * 0.55f, cy = h * 0.45f, rx = w * 0.22f, ry = h * 0.28f;
            g2.draw(new Arc2D.Float(cx - rx, cy - ry, rx * 2, ry * 2, 45, 270, Arc2D.OPEN));
            g2.drawLine((int) (cx - rx * 0.70f), (int) (cy + ry * 0.70f), (int) (w * 0.22f), (int) (h * 0.80f));
        });
    }

    // ─── Info / Details ──────────────────────────────────────────────────────
    public static Icon info(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            float pad = size * 0.12f;
            g2.setStroke(new BasicStroke(size * 0.09f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            float d = w - pad * 2;
            g2.draw(new Ellipse2D.Float(pad, pad, d, d));
            float dotR = size * 0.055f;
            g2.fill(new Ellipse2D.Float(w * 0.5f - dotR, h * 0.30f - dotR, dotR * 2, dotR * 2));
            g2.setStroke(new BasicStroke(size * 0.11f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine((int) (w * 0.5f), (int) (h * 0.44f), (int) (w * 0.5f), (int) (h * 0.72f));
        });
    }

    // ─── Chevron Right ───────────────────────────────────────────────────────
    public static Icon chevronRight(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(size * 0.14f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D p = new Path2D.Float();
            p.moveTo(w * 0.35f, h * 0.24f);
            p.lineTo(w * 0.65f, h * 0.50f);
            p.lineTo(w * 0.35f, h * 0.76f);
            g2.draw(p);
        });
    }

    // ─── Chevron Left ────────────────────────────────────────────────────────
    public static Icon chevronLeft(Color color, int size) {
        return new VectorIcon(size, size, (g2, w, h) -> {
            g2.setColor(color);
            g2.setStroke(new BasicStroke(size * 0.14f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Path2D p = new Path2D.Float();
            p.moveTo(w * 0.65f, h * 0.24f);
            p.lineTo(w * 0.35f, h * 0.50f);
            p.lineTo(w * 0.65f, h * 0.76f);
            g2.draw(p);
        });
    }
}
