/* This file was generated by SableCC (http://www.sablecc.org/). */

package ua.kiev.kpi.sc.parser.node;

import ua.kiev.kpi.sc.parser.analysis.*;

@SuppressWarnings("nls")
public final class AMultiCompilationUnit extends PCompilationUnit
{
    private PPublicClass _publicClass_;
    private PClassSeq _classSeq_;

    public AMultiCompilationUnit()
    {
        // Constructor
    }

    public AMultiCompilationUnit(
        @SuppressWarnings("hiding") PPublicClass _publicClass_,
        @SuppressWarnings("hiding") PClassSeq _classSeq_)
    {
        // Constructor
        setPublicClass(_publicClass_);

        setClassSeq(_classSeq_);

    }

    @Override
    public Object clone()
    {
        return new AMultiCompilationUnit(
            cloneNode(this._publicClass_),
            cloneNode(this._classSeq_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMultiCompilationUnit(this);
    }

    public PPublicClass getPublicClass()
    {
        return this._publicClass_;
    }

    public void setPublicClass(PPublicClass node)
    {
        if(this._publicClass_ != null)
        {
            this._publicClass_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._publicClass_ = node;
    }

    public PClassSeq getClassSeq()
    {
        return this._classSeq_;
    }

    public void setClassSeq(PClassSeq node)
    {
        if(this._classSeq_ != null)
        {
            this._classSeq_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._classSeq_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._publicClass_)
            + toString(this._classSeq_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._publicClass_ == child)
        {
            this._publicClass_ = null;
            return;
        }

        if(this._classSeq_ == child)
        {
            this._classSeq_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._publicClass_ == oldChild)
        {
            setPublicClass((PPublicClass) newChild);
            return;
        }

        if(this._classSeq_ == oldChild)
        {
            setClassSeq((PClassSeq) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}