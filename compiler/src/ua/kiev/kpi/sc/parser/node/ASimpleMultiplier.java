/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class ASimpleMultiplier extends PMultiplier
{
    private PCast _cast_;

    public ASimpleMultiplier()
    {
        // Constructor
    }

    public ASimpleMultiplier(
        @SuppressWarnings("hiding") PCast _cast_)
    {
        // Constructor
        setCast(_cast_);

    }

    @Override
    public Object clone()
    {
        return new ASimpleMultiplier(
            cloneNode(this._cast_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASimpleMultiplier(this);
    }

    public PCast getCast()
    {
        return this._cast_;
    }

    public void setCast(PCast node)
    {
        if(this._cast_ != null)
        {
            this._cast_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._cast_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._cast_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._cast_ == child)
        {
            this._cast_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._cast_ == oldChild)
        {
            setCast((PCast) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
