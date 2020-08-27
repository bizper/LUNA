package com.luna.core.constant;

public enum ESTATUS {

    /**
     * an element is be creating and ready for filling.
     * this status is very short during create process.
     * because when everything is filled finished, it will change to <code>PENDING</code>
     */
    CREATING,
    /**
     * creation finished. wait for pushing into pool.
     */
    PENDING,
    /**
     * stay in pool, wait to use
     */
    NOUSE,
    /**
     * the most appeared status.
     */
    USE,
    /**
     * wait for collecting.
     */
    WFC,
    /**
     * destroyed. if anything wants to use this(by pointer or any other), a Runtime Exception named UnreachableElementException will be thrown.
     */
    DESTROY

}
