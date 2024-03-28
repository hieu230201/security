package com.example.securitybase.util.delegates;

import java.sql.SQLException;

/**
 * @author caunv
 * @created_date 08/05/2021 - 5:56 PM
 */
public class FuncDelegate {
    @FunctionalInterface
    public interface Func_1<T1>{
        T1 doAction();
    }
    @FunctionalInterface
    public interface Func_2<T1, T2>{
        T2 doAction(T1 param1) throws SQLException;
    }
    @FunctionalInterface
    public interface Func_3<T1, T2, T3>{
        T3 doAction(T1 param1, T2 param2);
    }
    @FunctionalInterface
    public interface Func_4<T1, T2, T3, T4>{
        T4 doAction(T1 param1, T2 param2, T3 param3);
    }
    @FunctionalInterface
    public interface Func_5<T1, T2, T3,T4,T5>{
        T5 doAction(T1 param1, T2 param2, T3 param3, T4 param4);
    }
    @FunctionalInterface
    public interface Func_6<T1, T2, T3,T4,T5,T6>{
        T6 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5);
    }
    @FunctionalInterface
    public interface Func_7<T1, T2, T3,T4,T5,T6, T7>{
        T7 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6);
    }
    @FunctionalInterface
    public interface Func_8<T1, T2, T3,T4,T5,T6, T7, T8>{
        T8 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7);
    }
    @FunctionalInterface
    public interface Func_9<T1, T2, T3,T4,T5,T6, T7, T8, T9>{
        T9 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8);
    }
    @FunctionalInterface
    public interface Func_10<T1, T2, T3,T4,T5,T6, T7, T8, T9, T10>{
        T10 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9);
    }
    @FunctionalInterface
    public interface Func_11<T1, T2, T3,T4,T5,T6, T7, T8, T9, T10, T11>{
        T11 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9, T10 param10);
    }
    @FunctionalInterface
    public interface Func_12<T1, T2, T3,T4,T5,T6, T7, T8, T9, T10, T11, T12>{
        T12 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9, T10 param10, T11 param11);
    }
    @FunctionalInterface
    public interface Func_13<T1, T2, T3,T4,T5,T6, T7, T8, T9, T10, T11, T12, T13>{
        T13 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9, T10 param10, T11 param11, T12 param12);
    }
    @FunctionalInterface
    public interface Func_14<T1, T2, T3,T4,T5,T6, T7, T8, T9, T10, T11, T12, T13, T14>{
        T14 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9, T10 param10, T11 param11, T12 param12, T13 param13);
    }
    @FunctionalInterface
    public interface Func_15<T1, T2, T3,T4,T5,T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>{
        T15 doAction(T1 param1, T2 param2, T3 param3, T4 param4, T5 param5, T6 param6, T7 param7, T8 param8, T9 param9, T10 param10, T11 param11, T12 param12, T13 param13, T14 param14);
    }
}