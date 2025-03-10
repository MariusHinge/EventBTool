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

package com.viklauverk.eventbtools;

import com.viklauverk.eventbtools.core.BaseDocGen;
import com.viklauverk.eventbtools.core.DocGen;
import com.viklauverk.eventbtools.core.Log;
import com.viklauverk.eventbtools.core.LogModule;
import com.viklauverk.eventbtools.core.Machine;
import com.viklauverk.eventbtools.core.RenderTarget;
import com.viklauverk.eventbtools.core.Sys;

public class RunDocGen
{
    private static Log log = LogModule.lookup("docgen");

    public static void run(Settings s)
        throws Exception
    {
        Sys sys = new Sys();
        log.info("Loading machines and contexts from: %s", s.commonSettings().sourceDir());
        sys.loadMachinesAndContexts(s.commonSettings().sourceDir());

        BaseDocGen bdg = DocGen.lookup(s.commonSettings(), s.docGenSettings(), sys);

        String template = s.commonSettings().outputDir()+"/"+s.commonSettings().nickName()+"_template"+bdg.suffix();
        bdg.genTemplateFile(template);

        String out = s.commonSettings().outputDir()+"/"+s.commonSettings().nickName()+bdg.suffix();
        bdg.modFile(template, out);

        if (s.docGenSettings().renderTarget() == RenderTarget.TEX)
        {
            String idx = s.commonSettings().outputDir()+"/"+s.commonSettings().nickName()+".idx";

            log.info("Now run: xelatex "+out);
            log.info("         makeindex "+idx);
            log.info("         xelatex "+out);
        }
        if (s.docGenSettings().renderTarget() == RenderTarget.HTMQ)
        {
            String html = s.commonSettings().outputDir()+"/"+s.commonSettings().nickName()+".html";
            log.info("Now run: xmq "+out+" > "+html);
        }

    }

}
