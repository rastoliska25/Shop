package com.learn2code.shop.service.areaCalculator;

import java.util.List;

public class TruckFilling<T> {
    private static enum Fit {
        NOK,
        SUPER,
        OK
    }

    private Node root;

    public TruckFilling(int width, int height) {
        root = new Node(new Rectangle(0, 0, width, height));
    }

    public void getAllRectangles(List<Rectangle> rectangles) {
        root.getRectangles(rectangles);
    }

    public Rectangle findRectangle(T item) {
        return root.findRectange(item);
    }

    public void clearTruck() {
        root = new Node(root.rect);
    }

    public Rectangle insert(int width, int height, T o) {
        Node node = root.insert(width, height, o);

        if (node != null) {
            return new Rectangle(node.rect.x, node.rect.y,
                    node.rect.width, node.rect.height);
        } else {
            return null;
        }
    }

    public boolean remove(T o) {
        return root.remove(o);
    }

    public int getWidth() {
        return root.rect.width;
    }

    public int getHeight() {
        return root.rect.height;
    }

    private class Node {
        private final Rectangle rect;

        private T occupier = null;

        private Node left = null;

        private Node right = null;

        private Node(Rectangle r) {
            this.rect = r;
        }

        private Rectangle findRectange(T item) {
            if (isLeaf()) {
                if (item == occupier) {
                    return rect;
                } else {
                    return null;
                }
            } else {
                Rectangle l = left.findRectange(item);

                if (l != null) {
                    return l;
                } else {
                    return right.findRectange(item);
                }
            }
        }

        private Node insert(int width, int height, T o) {
            if (!isLeaf()) {
                Node r = left.insert(width, height, o);

                if (r == null) {
                    r = right.insert(width, height, o);
                }

                return r;
            } else {
                if (occupier != null) {
                    return null;
                }

                Fit fit = fits(width, height);

                switch (fit) {
                    case NOK:
                        return null;
                    case SUPER:
                        occupier = o;
                        return this;
                    case OK:
                        split(width, height);
                        break;
                }

                return left.insert(width, height, o);
            }
        }

        private boolean isLeaf() {
            return left == null;
        }

        private boolean isOccupied() {
            return occupier != null || !isLeaf();
        }
        private boolean remove(T o) {
            if (isLeaf()) {
                if (occupier == o) {
                    occupier = null;

                    return true;
                }
                return false;
            } else {
                boolean found = left.remove(o);
                if (!found) {
                    found = right.remove(o);
                }

                if (found) {
                    if (!left.isOccupied() && !right.isOccupied()) {
                        left = null;
                        right = null;
                    }
                }

                return found;
            }
        }

        private void split(int width, int height) {
            int dw = rect.width - width;
            int dh = rect.height - height;

            assert dw >= 0;
            assert dh >= 0;

            Rectangle r, l;
            if (dw > dh) {
                l = new Rectangle(rect.x, rect.y, width, rect.height);

                r = new Rectangle(l.x + width, rect.y, rect.width - width,
                        rect.height);
            } else {
                l = new Rectangle(rect.x, rect.y, rect.width, height);

                r = new Rectangle(rect.x, l.y + height, rect.width, rect.height
                        - height);
            }

            left = new Node(l);
            right = new Node(r);
        }

        private Fit fits(int width, int height) {
            if (width <= rect.width && height <= rect.height) {
                if (width == rect.width && height == rect.height) {
                    return Fit.SUPER;
                } else {
                    return Fit.OK;
                }
            }

            return Fit.NOK;
        }

        private void getRectangles(List<Rectangle> rectangles) {
            rectangles.add(rect);

            if (!isLeaf()) {
                left.getRectangles(rectangles);
                right.getRectangles(rectangles);
            }
        }
    }
}
