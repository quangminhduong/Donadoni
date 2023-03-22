package donadoni.dao;

public	class	BubbleSort	{
    public	static	Integer[]	sort(Integer[]	input)	{
        int	n	=	input.length;
        for	(int	i	=	0;	i	<	n	- 1;	i++)	{
            for	(int	j	=	0;	j	<	n	- i	- 1;	j++)	{
                if	(input[j]	>	input[j	+	1])	{
                    //	swap	j	and	j+1	elements
                    int	temp	=	input[j];
                    input[j]	=	input[j	+	1];
                    input[j	+	1]	=	temp;
                }
            }
        }
        return	input;
    }
}
