/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class AVariableClassBodyElem extends PClassBodyElem
{
    private PVariableDefinition _variableDefinition_;
    private TSemi _semi_;

    public AVariableClassBodyElem()
    {
        // Constructor
    }

    public AVariableClassBodyElem(
        @SuppressWarnings("hiding") PVariableDefinition _variableDefinition_,
        @SuppressWarnings("hiding") TSemi _semi_)
    {
        // Constructor
        setVariableDefinition(_variableDefinition_);

        setSemi(_semi_);

    }

    @Override
    public Object clone()
    {
        return new AVariableClassBodyElem(
            cloneNode(this._variableDefinition_),
            cloneNode(this._semi_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAVariableClassBodyElem(this);
    }

    public PVariableDefinition getVariableDefinition()
    {
        return this._variableDefinition_;
    }

    public void setVariableDefinition(PVariableDefinition node)
    {
        if(this._variableDefinition_ != null)
        {
            this._variableDefinition_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._variableDefinition_ = node;
    }

    public TSemi getSemi()
    {
        return this._semi_;
    }

    public void setSemi(TSemi node)
    {
        if(this._semi_ != null)
        {
            this._semi_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semi_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._variableDefinition_)
            + toString(this._semi_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._variableDefinition_ == child)
        {
            this._variableDefinition_ = null;
            return;
        }

        if(this._semi_ == child)
        {
            this._semi_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._variableDefinition_ == oldChild)
        {
            setVariableDefinition((PVariableDefinition) newChild);
            return;
        }

        if(this._semi_ == oldChild)
        {
            setSemi((TSemi) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
