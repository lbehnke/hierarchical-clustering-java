/*******************************************************************************
 * Copyright 2013 Lars Behnke
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.apporiented.algorithm.clustering;

import java.util.Iterator;
import java.util.function.Function;

public class LeafCluster<T> extends AbstractCluster<T> {

    private T name;
    private Function<T, String> toStringFunction;
    private Function<T, String> toIdFunction;

    public LeafCluster(T name, Function<T, String> toStringFunction) {
        this(name,toStringFunction,t->String.valueOf(t.hashCode()));
    }

    public LeafCluster(T name, Function<T, String> toStringFunction, Function<T, String> toIdFunction) {
        super(false);
        this.name = name;
        this.toStringFunction = toStringFunction;
        this.toIdFunction = toIdFunction;
    }


    //    @Override
    public T getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return "Cluster " + getClusterAsString();
    }

    public String getClusterAsString() {
        return toStringFunction.apply(getName());
    }

    @Override
    public T getPayload() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LeafCluster other = (LeafCluster) obj;
        if (getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!getName().equals(other.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public String getId(){
        return toIdFunction.apply(name);
    }

    @Override
    public int hashCode() {
        return (name == null) ? 0 : name.hashCode();
    }

}
