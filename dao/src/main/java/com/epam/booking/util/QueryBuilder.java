package com.epam.booking.util;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.util.Map;
import static com.epam.booking.util.ParameterConstant.*;

public class QueryBuilder {
    private static final String AVAILABLE_BASE_ROOM_HQL = "FROM Room r  " +
            "left outer join fetch r.reservations rentals " +
            "where (rentals.checkInDate < CURRENT_DATE AND rentals.checkOutDate < CURRENT_DATE " +
            "or rentals.room is null) ";
    private static final String BASE_ROOM_HQL = " FROM Room r ";
    private static final String BASE_ROOM_HQL_WITH_WHERE = " FROM Room r WHERE ";
    private static final String AND_KEYWORD = " AND ";

    private static int amountParamsInQuery;

    public static String createRoomQueryByParams(Map<String, String> params){
        if (params.isEmpty()){
            return BASE_ROOM_HQL;
        } else {
            StringBuilder resultQuery = createBaseQuery(params);
            verifyRoomType(params, resultQuery);
            verifyStartPrice(params, resultQuery);
            verifyEndPrice(params, resultQuery);
            return resultQuery.toString();
        }
    }

    private static StringBuilder createBaseQuery(Map<String, String> parameters){
        StringBuilder resultQuery = new StringBuilder();
        resetNumberOfParametersInQuery();
        if (parameters.containsKey(CHECK_IN_DATE) && parameters.containsKey(CHECK_OUT_DATE)
                && !StringUtils.isBlank(parameters.get(CHECK_IN_DATE)) &&
                !StringUtils.isBlank(parameters.get(CHECK_OUT_DATE))){
            verifyDates(parameters, resultQuery);
        } else{
            resultQuery = new StringBuilder(AVAILABLE_BASE_ROOM_HQL);
        }
        return resultQuery;
    }

    private static void verifyStartPrice(Map<String, String> parameters, StringBuilder resultQuery) {
        if (parameters.get(START_PRICE_PARAM) != null &&
                !StringUtils.isBlank(parameters.get(START_PRICE_PARAM))) {
            checkAndKeyword(resultQuery, parameters.size());
            int startPrice = Integer.parseInt(parameters.get(START_PRICE_PARAM));
            resultQuery.append(" r.price >= ").append(startPrice);
            amountParamsInQuery++;
        }
    }

    private static void verifyEndPrice(Map<String, String> parameters, StringBuilder resultQuery) {
        if (parameters.get(END_PRICE_PARAM) != null &&
                !StringUtils.isBlank(parameters.get(END_PRICE_PARAM))) {
            checkAndKeyword(resultQuery, parameters.size());
            int endPrice = Integer.parseInt(parameters.get(END_PRICE_PARAM));
            resultQuery.append(" r.price <= ").append(endPrice);
            amountParamsInQuery++;
        }
    }

    private static void verifyRoomType(Map<String, String> parameters, StringBuilder resultQuery){
        String type = parameters.get(TYPE_PARAM);
        if (type != null && !StringUtils.isBlank(type)){
            checkAndKeyword(resultQuery, parameters.size());
            String typeParam = type.toLowerCase();
            resultQuery.append(" lower(r.type) = '").append(typeParam).append("' ");
            amountParamsInQuery++;
        }
    }

    private static void checkAndKeyword(StringBuilder builder, int paramsAmount) {
        if (amountParamsInQuery == 0 || amountParamsInQuery < paramsAmount) {
            builder.append(AND_KEYWORD);
        }
    }

    private static void verifyDates(Map<String, String> parameters, StringBuilder resultQuery){
        String checkInDateParam = parameters.get(CHECK_IN_DATE);
        String checkOutDateParam = parameters.get(CHECK_OUT_DATE);
        if (checkInDateParam != null && checkOutDateParam != null &&
                !StringUtils.isBlank(checkInDateParam) && !StringUtils.isBlank(checkOutDateParam)){
            LocalDate checkInDate = LocalDate.parse(checkInDateParam);
            LocalDate checkOutDate = LocalDate.parse(checkOutDateParam);
            resultQuery.append("FROM Room r left outer join fetch r.reservations rentals " +
                            " where (rentals.checkInDate < TO_DATE('");
            resultQuery.append(checkInDate);
            resultQuery.append("', 'YYYY-MM-DD') AND rentals.checkOutDate < TO_DATE('");
            resultQuery.append(checkOutDate);
            resultQuery.append("', 'YYYY-MM-DD') or rentals.room is null or rentals.checkInDate > TO_DATE('");
            resultQuery.append(checkOutDate);
            resultQuery.append("', 'YYYY-MM-DD')) ");
            amountParamsInQuery++;
        }
    }

    private static void resetNumberOfParametersInQuery(){
        amountParamsInQuery = 0;
    }
}
