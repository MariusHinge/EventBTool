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

import com.viklauverk.eventbtools.core.Formula;

import java.util.List;
import java.util.LinkedList;

public class RenderTheoryTeX extends RenderTheoryUnicode
{
    @Override
    public void visit_TypeParametersStart(Theory th)
    {
        cnvs().append("\\subsection{\\footnotesize ");
        for (String tpn : th.typeParametersNames())
        {
            cnvs().set(tpn); //TODO Modify cnvs().set => cnvs().typeParam?
            cnvs().append(" ");
        }
        cnvs().append("}\n");
        super.visit_TypeParametersStart(th);
    }

    @Override
    public void visit_DatatypesStart(Theory th)
    {
        cnvs().append("\\subsection{\\footnotesize ");
        for (String dtn : th.datatypeNames())
        {
            cnvs().set(dtn); //TODO Modify cnvs().set => cnvs().datatype?
            cnvs().append(" ");
        }
        cnvs().append("}\n");
        super.visit_DatatypesStart(th);
    }

}