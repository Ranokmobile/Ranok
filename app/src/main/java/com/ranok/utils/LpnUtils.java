package com.ranok.utils;

import com.ranok.network.models.PlaceInfoModel;

public class LpnUtils {

    public static boolean isMoveEnabled(PlaceInfoModel item) {
        return item.getMayMove()==1 && item.getAvailQuantity() >= 1 && !(item.getLpn() == null || item.getLpn().isEmpty());
    }

    public static boolean isSplitEnabled(PlaceInfoModel item) {
        return item.getSysQuantity() >= 2 && item.getAvailQuantity() >= 1 && !(item.getLpn() == null || item.getLpn().isEmpty());
    }

    public static boolean isUnpackEnabled(PlaceInfoModel item) {
        return item.getMayUnpack()==1 && item.getAvailQuantity() >= 1 && !(item.getLpn() == null || item.getLpn().isEmpty());
    }

    public static boolean isPackEnabled(PlaceInfoModel item) {
        return item.getAvailQuantity() >= 1 && (item.getLpn() == null || item.getLpn().isEmpty());
    }

    public static boolean isLpnInfoEnabled(PlaceInfoModel item) {
        return (item.getLpn() != null && !item.getLpn().isEmpty());
    }

    public static boolean isPrintEnabled(PlaceInfoModel item) {
        return !(item.getLpn() == null || item.getLpn().isEmpty());
    }

    public static boolean isPlaceInfoEnabled(PlaceInfoModel item) {
        return (item.getAddress() != null && !item.getAddress().isEmpty());
    }

    public static boolean isPositionInfoEnabled(PlaceInfoModel item) {
        return (item.getItemCode() != null && !item.getItemCode().isEmpty());
    }

}
