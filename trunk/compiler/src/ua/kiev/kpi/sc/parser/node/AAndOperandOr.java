/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class AAndOperandOr extends POperandOr
{
    private POperandAnd _operandAnd_;
    private TAmpAmp _ampAmp_;
    private POperandOr _operandOr_;

    public AAndOperandOr()
    {
        // Constructor
    }

    public AAndOperandOr(
        @SuppressWarnings("hiding") POperandAnd _operandAnd_,
        @SuppressWarnings("hiding") TAmpAmp _ampAmp_,
        @SuppressWarnings("hiding") POperandOr _operandOr_)
    {
        // Constructor
        setOperandAnd(_operandAnd_);

        setAmpAmp(_ampAmp_);

        setOperandOr(_operandOr_);

    }

    @Override
    public Object clone()
    {
        return new AAndOperandOr(
            cloneNode(this._operandAnd_),
            cloneNode(this._ampAmp_),
            cloneNode(this._operandOr_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAAndOperandOr(this);
    }

    public POperandAnd getOperandAnd()
    {
        return this._operandAnd_;
    }

    public void setOperandAnd(POperandAnd node)
    {
        if(this._operandAnd_ != null)
        {
            this._operandAnd_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._operandAnd_ = node;
    }

    public TAmpAmp getAmpAmp()
    {
        return this._ampAmp_;
    }

    public void setAmpAmp(TAmpAmp node)
    {
        if(this._ampAmp_ != null)
        {
            this._ampAmp_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._ampAmp_ = node;
    }

    public POperandOr getOperandOr()
    {
        return this._operandOr_;
    }

    public void setOperandOr(POperandOr node)
    {
        if(this._operandOr_ != null)
        {
            this._operandOr_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._operandOr_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._operandAnd_)
            + toString(this._ampAmp_)
            + toString(this._operandOr_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._operandAnd_ == child)
        {
            this._operandAnd_ = null;
            return;
        }

        if(this._ampAmp_ == child)
        {
            this._ampAmp_ = null;
            return;
        }

        if(this._operandOr_ == child)
        {
            this._operandOr_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._operandAnd_ == oldChild)
        {
            setOperandAnd((POperandAnd) newChild);
            return;
        }

        if(this._ampAmp_ == oldChild)
        {
            setAmpAmp((TAmpAmp) newChild);
            return;
        }

        if(this._operandOr_ == oldChild)
        {
            setOperandOr((POperandOr) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
