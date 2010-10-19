function htmlEscape(html)
{
	var escaped= "";
	
	html = "" + html;
	
	var i, pos=0;
	for(i=0; i<html.length; i++)
	{
		if( !isAlfaNum( html.charAt(i) ) )
		{
			escaped += html.substring(pos, i);
			escaped += "&#" + html.charCodeAt(i) + ";";
			pos = i+1;
		}
	}
	escaped += html.substring(pos, html.length);

	return escaped;
}

function isAlfaNum(c)
{
	return isLetter(c) || isNumber(c) || isOtherAlphaNum(c);
}

function isOtherAlphaNum(c)
{
	switch (c)
	{
		case ' ':
		case '\t':
		case '\r':
		case '\n':
			return true;
	}
	return false;
}

function isLetter(c)
{
	return (c>='a' && c<='z') || (c>='A' && c<='Z');
}

function isNumber(c)
{
	return (c>='0' && c<='9');
}