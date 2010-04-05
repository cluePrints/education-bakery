/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class ANeqOperandAnd extends POperandAnd
{
    private PComparisonExpression _comparisonExpression_;
    private TNeq _neq_;
    private POperandAnd _operandAnd_;

    public ANeqOperandAnd()
    {
        // Constructor
    }

    public ANeqOperandAnd(
        @SuppressWarnings("hiding") PComparisonExpression _comparisonExpression_,
        @SuppressWarnings("hiding") TNeq _neq_,
        @SuppressWarnings("hiding") POperandAnd _operandAnd_)
    {
        // Constructor
        setComparisonExpression(_comparisonExpression_);

        setNeq(_neq_);

        setOperandAnd(_operandAnd_);

    }

    @Override
    public Object clone()
    {
        return new ANeqOperandAnd(
            cloneNode(this._comparisonExpression_),
            cloneNode(this._neq_),
            cloneNode(this._operandAnd_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANeqOperandAnd(this);
    }

    public PComparisonExpression getComparisonExpression()
    {
        return this._comparisonExpression_;
    }

    public void setComparisonExpression(PComparisonExpression node)
    {
        if(this._comparisonExpression_ != null)
        {
            this._comparisonExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._comparisonExpression_ = node;
    }

    public TNeq getNeq()
    {
        return this._neq_;
    }

    public void setNeq(TNeq node)
    {
        if(this._neq_ != null)
        {
            this._neq_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._neq_ = node;
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._comparisonExpression_)
            + toString(this._neq_)
            + toString(this._operandAnd_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._comparisonExpression_ == child)
        {
            this._comparisonExpression_ = null;
            return;
        }

        if(this._neq_ == child)
        {
            this._neq_ = null;
            return;
        }

        if(this._operandAnd_ == child)
        {
            this._operandAnd_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._comparisonExpression_ == oldChild)
        {
            setComparisonExpression((PComparisonExpression) newChild);
            return;
        }

        if(this._neq_ == oldChild)
        {
            setNeq((TNeq) newChild);
            return;
        }

        if(this._operandAnd_ == oldChild)
        {
            setOperandAnd((POperandAnd) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
