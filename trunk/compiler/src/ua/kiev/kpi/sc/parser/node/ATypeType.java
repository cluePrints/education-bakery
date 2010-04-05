/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class ATypeType extends PType
{
    private PTypeName _typeName_;

    public ATypeType()
    {
        // Constructor
    }

    public ATypeType(
        @SuppressWarnings("hiding") PTypeName _typeName_)
    {
        // Constructor
        setTypeName(_typeName_);

    }

    @Override
    public Object clone()
    {
        return new ATypeType(
            cloneNode(this._typeName_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseATypeType(this);
    }

    public PTypeName getTypeName()
    {
        return this._typeName_;
    }

    public void setTypeName(PTypeName node)
    {
        if(this._typeName_ != null)
        {
            this._typeName_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._typeName_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._typeName_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._typeName_ == child)
        {
            this._typeName_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._typeName_ == oldChild)
        {
            setTypeName((PTypeName) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
