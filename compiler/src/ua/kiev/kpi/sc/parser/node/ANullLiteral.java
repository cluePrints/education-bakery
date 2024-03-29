/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class ANullLiteral extends PLiteral
{
    private TLiteralNull _literalNull_;

    public ANullLiteral()
    {
        // Constructor
    }

    public ANullLiteral(
        @SuppressWarnings("hiding") TLiteralNull _literalNull_)
    {
        // Constructor
        setLiteralNull(_literalNull_);

    }

    @Override
    public Object clone()
    {
        return new ANullLiteral(
            cloneNode(this._literalNull_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANullLiteral(this);
    }

    public TLiteralNull getLiteralNull()
    {
        return this._literalNull_;
    }

    public void setLiteralNull(TLiteralNull node)
    {
        if(this._literalNull_ != null)
        {
            this._literalNull_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._literalNull_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._literalNull_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._literalNull_ == child)
        {
            this._literalNull_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._literalNull_ == oldChild)
        {
            setLiteralNull((TLiteralNull) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
