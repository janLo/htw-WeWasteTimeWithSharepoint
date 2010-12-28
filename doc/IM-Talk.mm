<map version="0.9.0">
<!-- To view this file, download free mind mapping software FreeMind from http://freemind.sourceforge.net -->
<node CREATED="1293561930928" ID="ID_1903825157" MODIFIED="1293562026193" TEXT="IM-Talk">
<node CREATED="1293561930929" ID="ID_598809266" MODIFIED="1293561930929" POSITION="right" TEXT="Problembeschreibung">
<richcontent TYPE="NOTE"><html><head /><body><p> Aufgabe: Für die Integration der Sharepoint Plattform in bestehende Anwendungen spielt das Thema Anwendungs-integration eine tragende Rolle. Der CIO wünscht sich daher eine Untersuchung der Webservice Funktionen.</p><p>  Exemplarisch soll hierfür eine Sharepoint-Liste via Webservices aus einer Java- und einer .NET-Anwendung heraus verwaltet (mind. Elemente anlegen, anzeigen, löschen) werden.</p></body></html></richcontent>
</node>
<node CREATED="1293561930930" ID="ID_1826478387" MODIFIED="1293561930930" POSITION="right" TEXT="Herangehensweise">
<node CREATED="1293561930930" ID="ID_334915164" MODIFIED="1293561930930" TEXT="Technologie Discover">
<richcontent TYPE="NOTE"><html><head /><body><p> Es muss erst untersucht werden welche technologien müssen und gehen um die Aufgabe zu lösen.</p></body></html></richcontent>
</node>
<node CREATED="1293561930931" ID="ID_1447580122" MODIFIED="1293561930931" TEXT="Beispielsuche">
<richcontent TYPE="NOTE"><html><head /><body><p> Das einfachste ist mit funktionierenden Beispielen zu staretn um ein Gefühl für die anstehenden Aufgaben sowie den Aufwand zu bekommen.</p></body></html></richcontent>
</node>
<node CREATED="1293561930931" ID="ID_1985225865" MODIFIED="1293561930931" TEXT="Suchen von Dokumentation">
<richcontent TYPE="NOTE"><html><head /><body><p> Es ist möglichst eindeutige Dokumentation möglichst vom Hersteler zu suchen.</p></body></html></richcontent>
</node>
<node CREATED="1293561930932" ID="ID_1469030764" MODIFIED="1293561930932" TEXT="Test in popeltestklasse">
<richcontent TYPE="NOTE"><html><head /><body><p> Es wurde eine art Testklasse angelegt, welche ausführbar war, in der die komplette Kommunikation mit dem Service vorher gestestet und reverse engineert wurde.</p></body></html></richcontent>
</node>
</node>
<node CREATED="1293561930932" ID="ID_1274353797" MODIFIED="1293561930932" POSITION="right" TEXT="Technologien">
<node CREATED="1293561930932" ID="ID_58049257" MODIFIED="1293561930932" TEXT="SOAP">
<richcontent TYPE="NOTE"><html><head /><body><p> XML Basierter RPC Mechnismus</p></body></html></richcontent>
<node CREATED="1293561930933" ID="ID_1142853300" MODIFIED="1293561930933" TEXT="WSDL">
<richcontent TYPE="NOTE"><html><head /><body><p> Web Services Description Language - Details lassen sich in der Wikipedia nachlesen. Beschreibt die Schnittstelle, Lässt sich mit AXIX benutzen um automatische bindings zu generieren.</p></body></html></richcontent>
</node>
</node>
<node CREATED="1293561930933" ID="ID_115889065" MODIFIED="1293561930933" TEXT="XML">
<richcontent TYPE="NOTE"><html><head /><body><p> Die Basistechnologie auf der SOAP aufbaut</p></body></html></richcontent>
</node>
<node CREATED="1293561930934" ID="ID_252119692" MODIFIED="1293561930934" TEXT="JAVA">
<node CREATED="1293561930934" ID="ID_547362774" MODIFIED="1293561930934" TEXT="AXIS">
<richcontent TYPE="NOTE"><html><head /><body><p> Generiert Java-Bindings aus dem Service-WSDL. Dies funktioniert zwar nur für request- und response-calls, die Typen und Werte die der Service liefert, bzw. welche man als Argumente übergeben muss kann sich der Entwickler selber aus dem geliefertem XML lutschen, bzw. eigenes XML zusammenfrickeln.</p></body></html></richcontent>
</node>
<node CREATED="1293561930934" ID="ID_1710362015" MODIFIED="1293561930934" TEXT="DOM">
<richcontent TYPE="NOTE"><html><head /><body><p> Das Java Dom Geraffel wird benutzt um XML-Fragmente zu erzeugen, welche in die Request objekte injected werden.</p><p>  Zudem wird der DOM Parser benötigt um die zurückgelieferten Fragmente zu verarbeiten und die relevanten Informationen herauszupopeln.</p></body></html></richcontent>
</node>
</node>
</node>
<node CREATED="1293561930935" ID="ID_1075643342" MODIFIED="1293562019079" POSITION="right" TEXT="Durchf&#xfc;hrung">
<node CREATED="1293561930935" ID="ID_700241738" MODIFIED="1293561930935" TEXT="Interpretetion der Aufgabe auftellen">
<node CREATED="1293561930935" ID="ID_605624201" MODIFIED="1293561930935" TEXT="Was sind Elemente?">
<richcontent TYPE="NOTE"><html><head /><body><p> In der Aufgabenstellung wird von Elementen gesprohen, welche bearbeitet werden sollen. Wir definieren Elemente als Listeneinträge</p></body></html></richcontent>
</node>
<node CREATED="1293561930936" ID="ID_1010391135" MODIFIED="1293561930936" TEXT="Wie weit sollen Listen frei w&#xe4;hlbar sein?">
<richcontent TYPE="NOTE"><html><head /><body><p> Man muss natürlich eine Liste auswählen die man bearbeiten will.</p><p>  Es wurde festgelegt das ein Subset der verfügbaren Listen einfach zur Auswahl gestellt werden und der Nuter selektieren kann. Das Auswahlkriterium wurde dabei mehr oder minder beliebig darauf festgelegt, das der Author nicht die ID 1 haben darf.</p><p>  In wie weit das sinvoll ist kann und will ich nicht beurteilen.</p></body></html></richcontent>
</node>
</node>
<node CREATED="1293561930937" ID="ID_422187583" MODIFIED="1293561930937" TEXT="Autom. generieren von Bindings">
<richcontent TYPE="NOTE"><html><head /><body><p> Verwendung von Apache axis, durch die Netbeans IDE</p></body></html></richcontent>
</node>
<node CREATED="1293561930937" ID="ID_966760746" MODIFIED="1293561930937" TEXT="Immer wieder testen und entsprechend reverse engineeren">
<richcontent TYPE="NOTE"><html><head /><body><p> Anfragen wurden zuerst in einer Testklasse gestestet und anschließend die Antworten manuell analysiert und Annahmen getroffen wo was stehen könnte. Dies wurde entsprechend oft wiederholt bis man sich halbwegs sicher sein konnte das das angenommene Format der Antwort keinen allzugroßen Offset zur Realität hat.</p></body></html></richcontent>
</node>
<node CREATED="1293561930938" ID="ID_1970698265" MODIFIED="1293561930938" TEXT="Eine &#x201e;Architektur&#x201c; erdenken">
<richcontent TYPE="NOTE"><html><head /><body><p> Es gibt einen Connector, der die Kommunikation mit dem Servce übernimmt, Interfaces für Listen und Listenelemente und einen Adapter der aus einer Liste ein TableModel zur Verfügung stellt.</p><p>  Der Connector fungiert als eine Art Factory für Listen, indem er die vom Service gelieferten Daten in einer Implemetierung der Interfaces verpakt.</p><p>  Dann gibt es noch das ganze GUI Geraffel und die automatisch generierten Bindings.</p></body></html></richcontent>
</node>
<node CREATED="1293561930939" ID="ID_1220869837" MODIFIED="1293561930939" TEXT="ca 30-40h Debugging und Codescreiben">
<richcontent TYPE="NOTE"><html><head /><body><p> Das Schreiben des Codes ging relativ Straight-Forward. Es wurde lediglich durch die ständigen Probierereien gestört, welche die mangelhafte Doku ersetzen mussten.</p></body></html></richcontent>
</node>
<node CREATED="1293561930939" ID="ID_495455899" MODIFIED="1293561930939" TEXT="Basis Akzeptanztests (manuell)">
<richcontent TYPE="NOTE"><html><head /><body><p> Implementierte Funktionalität wurde im nachhinein einem groben Akzeptanztest unterzogen indem hinreichend oft geklickt wurde und bei einem Ausbleiben von Fehlern das ganze für gut befunden wurde.</p></body></html></richcontent>
</node>
</node>
<node CREATED="1293561930940" ID="ID_1380549013" MODIFIED="1293561930940" POSITION="right" TEXT="Probleme (Implementierung)">
<node CREATED="1293561930940" ID="ID_485325592" MODIFIED="1293561930940" TEXT="Authentifizierung">
<richcontent TYPE="NOTE"><html><head /><body><p> Sharepoint benutzt NTLM Auth - Java kann das nicht. Lösung: Sharepoint auf das BASIC Verfahren umstellen. Belannte Seiteneffekte: …?</p></body></html></richcontent>
</node>
<node CREATED="1293561930941" ID="ID_1046642479" MODIFIED="1293561930941" TEXT="Binding">
<richcontent TYPE="NOTE"><html><head /><body><p> Bindings werden aus dem WSDL generiert -&gt; Allerdings nur für die Schnittstelle. Content muss „per Hand“ mit gänigen XML-Parsern/Generatoren zerpflückt bzw. erzeugt werden.</p></body></html></richcontent>
</node>
<node CREATED="1293561930941" ID="ID_938894856" MODIFIED="1293561930941" TEXT="Sp&#xe4;rliche Dokumentation">
<richcontent TYPE="NOTE"><html><head /><body><p> benutzte XML Notationen oft demantisch nicht erklärt -&gt; Reverse engineering. Nichtssagende Fehlermeldungen („Unauthorized“, „Eine Ausnahme vom Typ "Microsoft.SharePoint.SoapServer.SoapServerException" wurde ausgelöst.“</p></body></html></richcontent>
</node>
</node>
<node CREATED="1293561930941" ID="ID_1642658614" MODIFIED="1293561930941" POSITION="right" TEXT="Known Issues (Resultat)">
<node CREATED="1293561930942" ID="ID_80463221" MODIFIED="1293561930942" TEXT="Infrastruktur">
<richcontent TYPE="NOTE"><html><head /><body><p> Da es sich um proprietäre Technologie handelt deren Sicherheitsrisiko nicht überschaubar ist und die zudem noch von einer Firma kommt, welche für eklatante Sicherheitsmängel bekannt ist und gar noch auf einem Betriebsystem der gleichen Firma in einem der unsichersten Webserver laufen muss, entschied wohl der zuständige Administrator den Dienst nicht nach ausse verfübbar zu machen.</p><p>  Man erreicht ihn somit nur duch das VPN zur Hochschule, welches aufgrund der Einschränkungen der verwendeten Proprietären technologien und der Ignoranz sowie Inkompetenz der betreuenden Administratoren aller 180 Minuten getrennt wird.</p><p>  Leider ist eine dauerhafte Verbindung während der Entwicklung aufgrund der mangelhaften Dokumentation notwendig.</p></body></html></richcontent>
</node>
<node CREATED="1293561930942" ID="ID_749864887" MODIFIED="1293561930942" TEXT="BASIC Auth">
<richcontent TYPE="NOTE"><html><head /><body><p> The only prooved</p></body></html></richcontent>
</node>
<node CREATED="1293561930943" ID="ID_1917087159" MODIFIED="1293561930943" TEXT="Hardcoded URL">
<richcontent TYPE="NOTE"><html><head /><body><p> Domain und Urlanteil sind momentan hart im code festgelegt.</p></body></html></richcontent>
</node>
<node CREATED="1293561930943" ID="ID_572684292" MODIFIED="1293561930943" TEXT="Typunsicher">
<richcontent TYPE="NOTE"><html><head /><body><p> Aller Inhalt wird atm als TEXT behandelt. Es gibt keine Unterscheidung zwischen Datentypen. Dies aus dem Listen-AML zu lesen scheint aufwendig und ist UNDOKUMENTIERT!</p></body></html></richcontent>
</node>
<node CREATED="1293561930944" ID="ID_1439419518" MODIFIED="1293561930944" TEXT="! ACID">
<richcontent TYPE="NOTE"><html><head /><body><p> Nicht transaktionssicher, Elemente werden nicht gesperrt.</p></body></html></richcontent>
</node>
</node>
<node CREATED="1293561930944" ID="ID_201563642" MODIFIED="1293561930944" POSITION="right" TEXT="Lessons learned">
<node CREATED="1293561930944" ID="ID_1523971452" MODIFIED="1293561930944" TEXT="Propriet&#xe4;re Systeme sind gef&#xe4;hrlich">
<richcontent TYPE="NOTE"><html><head /><body><p> Man ist bei den Schnittstellen zu 100% auf die mitgelieferte Dokumentation oder existierende Beispiele angewiesen. Dinge die in der Dokumentation nicht hinreichend behandelt werden müssen mittels Ausprobieren erforscht werden, was jedoch duch mangelnde Kenntniss von CornerCases zu zerbrechlicher Software führt.</p><p>  Ein Blick auf interne Details ist schlichtweg nicht möglich. Meine Erfahrungen zeigen jedoch das dies eine nicht zu ersezende Hilfe ist.</p><p>  Schnittstellen != offene Schnittstellen</p></body></html></richcontent>
</node>
<node CREATED="1293561930945" ID="ID_1526823755" MODIFIED="1293561930945" TEXT="Zeitsch&#xe4;tzungen f&#xfc;r solche Aufgaben sind nicht m&#xf6;glich.">
<richcontent TYPE="NOTE"><html><head /><body><p> Es kann alles an Problemen auftreten, manche sind in 3 Minuten gelöst, bei anderen stellt man nach 5h fest das man wohl einen anderen Ansatz wählen muss.</p></body></html></richcontent>
</node>
</node>
</node>
</map>
