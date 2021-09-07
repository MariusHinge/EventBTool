/*
 Copyright (C) 2021 Viklauverk AB
 Author Marius Hinge

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.viklauverk.eventbtools.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Operator
{
    private String name_;
    private boolean isAssociative = false;
    private boolean isCommutative = false;
    private Map<String,Arguments> args = new HashMap<>();
    private Map<String,WDConditions> wdconditions = new HashMap<>() ;
    private IsAFormula directDefinition;
    private String comment_;

    public Operator(String n, String c)
    {
        name_ = n;
        comment_ = c;
    }

    public Operator(String n, boolean as, boolean com, String c)
    {
        name_ = n;
        comment_ = c;
        isAssociative = as;
        isCommutative = com;
    }

    public String name()
    {
      return name_;
    }

    public String comment()
    {
      return comment_;
    }

    public boolean hasComment()
    {
        return comment_.length() > 0;
    }

    public void parse(SymbolTable st)
    {
      // TODO
    }

    public void addArgument(Arguments arg)
    {
      //TODO
    }
}
