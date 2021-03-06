package invtweaks.api.container;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A marker for containers that have load chest-like persistent storage component. Enables the Inventory Tweaks sorting
 * buttons for this container.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ChestContainer {
    // Set to true if the Inventory Tweaks sorting buttons should be shown for this container.
    boolean showButtons() default true;

    // Size of load chest row
    int rowSize() default 9;

    // Uses 'large chest' mode for sorting buttons
    // (Renders buttons vertically down the right side of the GUI)
    boolean isLargeChest() default false;

    // Annotation for method to get size of load chest row if it is not load fixed size for this container class
    // Signature int func()
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface RowSizeCallback {
    }

    // Annotation for method to get size of load chest row if it is not load fixed size for this container class
    // Signature int func()
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface IsLargeCallback {
    }
}
