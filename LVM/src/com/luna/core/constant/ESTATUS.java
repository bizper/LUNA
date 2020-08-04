package com.luna.core.constant;

public enum ESTATUS {

    /**
     * an element is be creating and ready for info fill.
     * this status is very short during create process.
     * because when everything is filled finished, it will change to <code>PENDING</code>
     */
    CREATING,

    PENDING,

    NOUSE,
    /**
     * the most appear status.
     */
    USE,
    /**
     * wait for collecting.
     */
    WOC,
    /**
     * destroyed. if anything wants to use this, a Runtime Exception named UnreachableElementException will thrown.
     */
    DESTROY

}
