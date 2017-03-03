package gui.event;

import data.Point_Float;

public class Skyline_Query_Event extends My_Event{

	public Point_Float point;
	
	public Skyline_Query_Event(Point_Float point) {
		this.point = point;
	}
	
}
