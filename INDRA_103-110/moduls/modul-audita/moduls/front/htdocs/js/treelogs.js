
	USETEXTLINKS = 1  //replace 0 with 1 for hyperlinks
	STARTALLOPEN = 1 //replace 0 with 1 to show the whole tree
	ICONPATH = CONTEXTPATH +'/images/treeview/' 
	USEFRAMES = 0
	WRAPTEXT = 0
	USEICONS = 0
	
	icoraiz = CONTEXTPATH + '/config/images/lupa.gif'
	icointerno = CONTEXTPATH + '/config/images/i08_buzon.gif'
	icoinstancia = CONTEXTPATH + '/config/images/i03_docu_ok.gif'	
	
	foldersTree = gFld(" Logs ", "")
	
	foldersTree.iconSrc = icoraiz	
	foldersTree.estilo = "granate"	
	
	foldersTree = gFld(" Logs ", "")
	
	foldersTree.iconSrc = icoraiz	
	foldersTree.estilo = "granate"			

	

	// Errores de usuario
      	doc1 = insDoc(foldersTree, gLnk("S", "Log de errores de usuario", "javascript:abrirLog(5)"))
      	doc1.iconSrc = icoinstancia
      	doc1.iconSrcClosed = icoinstancia
      	doc1.estilo = "naranja"
      	
      	// BD
      	aux1 = gFld("Logs de base de datos", "")
      	doc1 = insFld(foldersTree,aux1)
      	doc1.iconSrc = icointerno
      	doc1.iconSrcClosed = icointerno
      	doc1.estilo = "granate"
      	
      	aux2 = insDoc(doc1, gLnk("S", "Log de accesos a conexion", "javascript:abrirLog(1)"))
      	aux2.iconSrc = icoinstancia
      	aux2.iconSrcClosed = icoinstancia
      	aux2.estilo = "naranja"
      	
      	aux2 = insDoc(doc1, gLnk("S", "Log de conexiones recolectadas", "javascript:abrirLog(2)"))
      	aux2.iconSrc = icoinstancia
      	aux2.iconSrcClosed = icoinstancia
      	aux2.estilo = "naranja"
      	
      	// API
      	doc1 = insDoc(foldersTree, gLnk("S", "Log de peticiones al API Distribuida", "javascript:abrirLog(3)"))
      	doc1.iconSrc = icoinstancia
      	doc1.iconSrcClosed = icoinstancia
      	doc1.estilo = "naranja"
      	
      	
      	// API CLIENTE
      	doc1 = insDoc(foldersTree, gLnk("S", "Log cliente API", "javascript:abrirLog(10)"))
      	doc1.iconSrc = icoinstancia
      	doc1.iconSrcClosed = icoinstancia
      	doc1.estilo = "naranja"
      	
      	// LOGSERVLET
      	doc1 = insDoc(foldersTree, gLnk("S", "Log Servlet", "javascript:abrirLog(9)"))
      	doc1.iconSrc = icoinstancia
      	doc1.iconSrcClosed = icoinstancia
      	doc1.estilo = "naranja"
      	
      	// Sesiones HTTP
      	aux1 = gLnk("S", "Log de sesiones Http", "javascript:abrirLog(4)")
      	
      	doc1 = insDoc(foldersTree, aux1)
      	doc1.iconSrc = icoinstancia
      	doc1.iconSrcClosed = icoinstancia
      	doc1.estilo = "naranja"

	// Notificador
	doc1 = insDoc(foldersTree, gLnk("S", "Log de notificador", "javascript:abrirLog(8)"))
      	doc1.iconSrc = icoinstancia
      	doc1.iconSrcClosed = icoinstancia
      	doc1.estilo = "naranja"