package ru.r8digitaltech7.onlylemi.mapview.library;

/**
 * MapViewListener
 *
 * @author: onlylemi
 */
public interface MapViewListener {

    /**
     * when mapview load complete to callback
     */
    void onMapLoadSuccess();

    /**
     * when mapview load error to callback
     */
    void onMapLoadFail();
}
