package de.jaggl.utils.sqlbuilder.domain;

public class PartMissingException extends RuntimeException
{

    private static final long serialVersionUID = -4061548217795884312L;

    public PartMissingException(String message)
    {
        super(message);
    }

}
