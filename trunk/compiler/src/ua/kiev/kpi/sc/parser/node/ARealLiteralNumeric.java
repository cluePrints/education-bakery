/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class ARealLiteralNumeric extends PLiteralNumeric
{
    private PInteger _integer_;
    private TDot _dot_;
    private TNonNegativeInteger _nonNegativeInteger_;

    public ARealLiteralNumeric()
    {
        // Constructor
    }

    public ARealLiteralNumeric(
        @SuppressWarnings("hiding") PInteger _integer_,
        @SuppressWarnings("hiding") TDot _dot_,
        @SuppressWarnings("hiding") TNonNegativeInteger _nonNegativeInteger_)
    {
        // Constructor
        setInteger(_integer_);

        setDot(_dot_);

        setNonNegativeInteger(_nonNegativeInteger_);

    }

    @Override
    public Object clone()
    {
        return new ARealLiteralNumeric(
            cloneNode(this._integer_),
            cloneNode(this._dot_),
            cloneNode(this._nonNegativeInteger_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseARealLiteralNumeric(this);
    }

    public PInteger getInteger()
    {
        return this._integer_;
    }

    public void setInteger(PInteger node)
    {
        if(this._integer_ != null)
        {
            this._integer_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._integer_ = node;
    }

    public TDot getDot()
    {
        return this._dot_;
    }

    public void setDot(TDot node)
    {
        if(this._dot_ != null)
        {
            this._dot_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._dot_ = node;
    }

    public TNonNegativeInteger getNonNegativeInteger()
    {
        return this._nonNegativeInteger_;
    }

    public void setNonNegativeInteger(TNonNegativeInteger node)
    {
        if(this._nonNegativeInteger_ != null)
        {
            this._nonNegativeInteger_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._nonNegativeInteger_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._integer_)
            + toString(this._dot_)
            + toString(this._nonNegativeInteger_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._integer_ == child)
        {
            this._integer_ = null;
            return;
        }

        if(this._dot_ == child)
        {
            this._dot_ = null;
            return;
        }

        if(this._nonNegativeInteger_ == child)
        {
            this._nonNegativeInteger_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._integer_ == oldChild)
        {
            setInteger((PInteger) newChild);
            return;
        }

        if(this._dot_ == oldChild)
        {
            setDot((TDot) newChild);
            return;
        }

        if(this._nonNegativeInteger_ == oldChild)
        {
            setNonNegativeInteger((TNonNegativeInteger) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}