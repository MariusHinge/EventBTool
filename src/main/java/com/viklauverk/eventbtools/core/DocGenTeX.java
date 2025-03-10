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

import java.net.URL;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DocGenTeX extends BaseDocGen
{
    private static Log log = LogModule.lookup("tex");

    public DocGenTeX(CommonSettings common_settings, DocGenSettings docgen_settings, Sys sys)
    {
        super(common_settings, docgen_settings, sys, "tex");
    }

    @Override
    public String suffix()
    {
        return ".tex";
    }

    @Override
    public String generateDocument() throws Exception
    {
        Canvas cnvs = new Canvas();
        cnvs.setRenderTarget(RenderTarget.TEX);

        cnvs.append(Templates.TeXHeader.replace("$PATH_TO_IMAGES$", commonSettings().sourceDir()+"/"));

        cnvs.append("EVBT(show template \"TeXDefinitions\")\n");

        cnvs.append("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
        cnvs.append("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
        cnvs.append("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");

        cnvs.append("\\makeindex\n");
        cnvs.append("\\begin{document}\n");

        cnvs.append("\\raggedright\n");

        cnvs.append("{\\Large An Event-B Specification of} \\\\\n");
        cnvs.append("\\vspace{2mm}\n");
        cnvs.append("{\\Huge "+Util.texSafe(commonSettings().nickName())+"} \\\\\n");
        cnvs.append("\\HRULE\n\n");

        if (sys().projectInfo().length() > 0)
        {
            cnvs.append("\n\n\\par ");
            cnvs.append(Unicode.commentToTeX(sys().projectInfo()));
            cnvs.append("\n\n\\HRULE\n\n");
        }

        cnvs.append("\\tableofcontents\n");

        for (String ctx : sys().contextNames())
        {
            // Skip EDK contexts for the moment.
            if (ctx.startsWith("EDK_")) continue;

            cnvs.append("\\pagebreak\n\n");
            cnvs.append("\\section{\\KEYWL{CONTEXT}\\small\\ "+Util.texSafe(ctx)+"}\n\n");
            cnvs.append("EVBT(show part tex \""+ctx+"\")\n");
        }

        for (String mch : sys().machineNames())
        {
            cnvs.append("\\pagebreak\n\n");
            Machine m = sys().getMachine(mch);
            cnvs.append("\\section{\\KEYWL{"+m.machineOrRefinement().toUpperCase()+"}\\small\\ "+Util.texSafe(mch)+"}\n\n");
            cnvs.append("EVBT(show part tex \""+mch+"\")\n");
        }

        cnvs.append("\\printindex\n");

        cnvs.append("\\end{document}\n");
        return cnvs.render();
    }
}
