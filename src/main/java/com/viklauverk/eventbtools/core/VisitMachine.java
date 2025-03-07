/*
 Copyright (C) 2021 Viklauverk AB
 Author Fredrik Öhrström

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

public class VisitMachine
{
    public static void walk(RenderMachine v, Machine mch, String pattern)
    {
        boolean m =  Util.match(mch.name()+"/", pattern);

        if (m) v.visit_MachineStart(mch);

        if (m && mch.hasRefines())
        {
            v.visit_RefinesStart(mch);
            v.visit_Refines(mch, mch.refines());
            v.visit_RefinesEnd(mch);
        }

        if (m && mch.hasContexts())
        {
            v.visit_SeesStart(mch);
            for (Context c : mch.contextOrdering())
            {
                v.visit_Sees(mch, c);
            }
            v.visit_SeesEnd(mch);
        }

        if (m) v.visit_HeadingComplete(mch);

        if (mch.hasVariables())
        {
            boolean vs =  Util.match(mch.name()+"/variables/", pattern);

            if (vs) v.visit_VariablesStart(mch);
            for (Variable var : mch.variableOrdering())
            {
                boolean vv =  Util.match(mch.name()+"/variables/"+var.name()+"/", pattern);
                if (vv) v.visit_Variable(mch, var);
            }
            if (vs) v.visit_VariablesEnd(mch);
        }

        if (mch.hasInvariants())
        {
            boolean i =  Util.match(mch.name()+"/invariants/", pattern);

            if (i) v.visit_InvariantsStart(mch);
            for (Invariant inv : mch.invariantOrdering())
            {
                boolean ii =  Util.match(mch.name()+"/invariants/"+inv.name()+"/", pattern);
                if (ii) v.visit_Invariant(mch, inv);
            }
            if (i) v.visit_InvariantsEnd(mch);
        }

        if (mch.hasVariants())
        {
            boolean vs =  Util.match(mch.name()+"/variants/", pattern);

            if (vs) v.visit_VariantsStart(mch);
            for (Variant var : mch.variantOrdering())
            {
                boolean vv =  Util.match(mch.name()+"/variants/"+var.name()+"/", pattern);
                if (vv) v.visit_Variant(mch, var);
            }
            if (vs) v.visit_VariantsEnd(mch);
        }

        if (mch.hasTheorems())
        {
            boolean t =  Util.match(mch.name()+"/theorems", pattern);

            if (t) v.visit_TheoremsStart(mch);
            for (Theorem the : mch.theoremOrdering())
            {
                boolean tt =  Util.match(mch.name()+"/theorems/"+the.name()+"/", pattern);
                if (tt) v.visit_Theorem(mch, the);
            }
            if (t) v.visit_TheoremsEnd(mch);
        }


        if (mch.hasEvents())
        {
            String name = mch.name()+"/events/";
            boolean e = Util.match(name, pattern);

            if (e) v.visit_EventsStart(mch);
            for (Event eve : mch.eventOrdering())
            {
                /*
                String p = pattern;
                if (p.equals(name) || p.equals(mch.name()))
                {
                    p = "";
                }
                else if (p.length() > name.length())
                {
                    // The pattern is Elevator/events/moveDown/grd0_1
                    // cut the pattern to just moveDown/grd0_1 so that it works
                    // within the event visitor.
                    p = p.substring(name.length()+1);
                }
                */
                v.visit_Event(mch, eve, pattern);
            }
            if (e) v.visit_EventsEnd(mch);
        }

        if (m) v.visit_MachineEnd(mch);
    }

}
