/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class ARemSummand extends PSummand
{
    private PMultiplier _multiplier_;
    private TPercent _percent_;
    private PSummand _summand_;

    public ARemSummand()
    {
        // Constructor
    }

    public ARemSummand(
        @SuppressWarnings("hiding") PMultiplier _multiplier_,
        @SuppressWarnings("hiding") TPercent _percent_,
        @SuppressWarnings("hiding") PSummand _summand_)
    {
        // Constructor
        setMultiplier(_multiplier_);

        setPercent(_percent_);

        setSummand(_summand_);

    }

    @Override
    public Object clone()
    {
        return new ARemSummand(
            cloneNode(this._multiplier_),
            cloneNode(this._percent_),
            cloneNode(this._summand_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseARemSummand(this);
    }

    public PMultiplier getMultiplier()
    {
        return this._multiplier_;
    }

    public void setMultiplier(PMultiplier node)
    {
        if(this._multiplier_ != null)
        {
            this._multiplier_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._multiplier_ = node;
    }

    public TPercent getPercent()
    {
        return this._percent_;
    }

    public void setPercent(TPercent node)
    {
        if(this._percent_ != null)
        {
            this._percent_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._percent_ = node;
    }

    public PSummand getSummand()
    {
        return this._summand_;
    }

    public void setSummand(PSummand node)
    {
        if(this._summand_ != null)
        {
            this._summand_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._summand_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._multiplier_)
            + toString(this._percent_)
            + toString(this._summand_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._multiplier_ == child)
        {
            this._multiplier_ = null;
            return;
        }

        if(this._percent_ == child)
        {
            this._percent_ = null;
            return;
        }

        if(this._summand_ == child)
        {
            this._summand_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._multiplier_ == oldChild)
        {
            setMultiplier((PMultiplier) newChild);
            return;
        }

        if(this._percent_ == oldChild)
        {
            setPercent((TPercent) newChild);
            return;
        }

        if(this._summand_ == oldChild)
        {
            setSummand((PSummand) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}