/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class TDoubleQuote extends Token
{
    public TDoubleQuote()
    {
        super.setText("\"");
    }

    public TDoubleQuote(int line, int pos)
    {
        super.setText("\"");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TDoubleQuote(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTDoubleQuote(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TDoubleQuote text.");
    }
}
